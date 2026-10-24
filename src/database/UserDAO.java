
package database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public void addUser(
            String username,
            String password,
            String role,
            String userId) {

        String sql = "INSERT INTO users "
                   + "(username, password, role, user_id) "
                   + "VALUES (?, ?, ?, ?)";

        String hashedPassword = PasswordUtil.hashPassword(password);

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                System.out.println("Database connection unavailable.");
                return;
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(sql)) {

                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                statement.setString(3, role);
                statement.setString(4, userId);

                statement.executeUpdate();
                System.out.println("User added successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Could not add user: " + e.getMessage());
        }
    }

    public boolean validateLogin(
            String username,
            String password,
            String role) {

        String sql = "SELECT password FROM users "
                   + "WHERE username = ? AND role = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                return false;
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(sql)) {

                statement.setString(1, username);
                statement.setString(2, role);

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (!resultSet.next()) {
                        return false;
                    }

                    String storedPassword =
                        resultSet.getString("password");

                    return verifyAndMigratePassword(
                        connection,
                        username,
                        role,
                        password,
                        storedPassword
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Login validation failed.");
            return false;
        }
    }

    public String getUserId(
            String username,
            String password,
            String role) {

        String sql = "SELECT user_id, password FROM users "
                   + "WHERE username = ? AND role = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                return null;
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(sql)) {

                statement.setString(1, username);
                statement.setString(2, role);

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (!resultSet.next()) {
                        return null;
                    }

                    String storedPassword =
                        resultSet.getString("password");

                    boolean valid = verifyAndMigratePassword(
                        connection,
                        username,
                        role,
                        password,
                        storedPassword
                    );

                    return valid ? resultSet.getString("user_id") : null;
                }
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve user ID.");
            return null;
        }
    }

    private boolean verifyAndMigratePassword(
            Connection connection,
            String username,
            String role,
            String password,
            String storedPassword) throws SQLException {

        if (password == null || storedPassword == null) {
            return false;
        }

        // Hashed passwords use the format iterations:salt:hash.
        if (storedPassword.contains(":")) {
            return PasswordUtil.verifyPassword(password, storedPassword);
        }

        // Temporary compatibility for existing plain-text accounts.
        boolean matches = MessageDigest.isEqual(
            password.getBytes(StandardCharsets.UTF_8),
            storedPassword.getBytes(StandardCharsets.UTF_8)
        );

        if (!matches) {
            return false;
        }

        // Upgrade a valid legacy password to a salted hash.
        String updateSql = "UPDATE users SET password = ? "
                         + "WHERE username = ? AND role = ?";

        try (PreparedStatement update =
                connection.prepareStatement(updateSql)) {

            update.setString(1, PasswordUtil.hashPassword(password));
            update.setString(2, username);
            update.setString(3, role);
            update.executeUpdate();

        } catch (SQLException e) {
            // Allow this login, but the old password may remain un-hashed.
            System.out.println(
                "Password migration failed. Please try again later."
            );
        }

        return true;
    }

    public void deleteUser(String username) {

        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                System.out.println("Database connection unavailable.");
                return;
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(sql)) {

                statement.setString(1, username);
                statement.executeUpdate();
                System.out.println("User deleted successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Could not delete user: " + e.getMessage());
        }
    }
}
