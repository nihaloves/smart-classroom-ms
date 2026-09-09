package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public void addUser(String username,
                        String password,
                        String role,
                        String userId) {

        String sql = "INSERT INTO users " +
                     "(username, password, role, user_id) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.setString(4, userId);

            statement.executeUpdate();

            System.out.println("User added successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add user.");
            e.printStackTrace();
        }
    }

    public boolean validateLogin(String username,
                                 String password,
                                 String role) {

        String sql = "SELECT * FROM users " +
                     "WHERE username = ? AND password = ? AND role = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    System.out.println("Login successful!");
                    return true;
                }

            }

        } catch (Exception e) {
            System.out.println("Login validation failed.");
            e.printStackTrace();
        }

        System.out.println("Invalid login credentials.");
        return false;
    }

    public String getUserId(String username,
                             String password,
                             String role) {

        String sql = "SELECT user_id FROM users " +
                     "WHERE username = ? AND password = ? AND role = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("user_id");
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve user ID.");
            e.printStackTrace();
        }

        return null;
    }

    public void deleteUser(String username) {

        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("User not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete user.");
            e.printStackTrace();
        }
    }
}