package database;

import model.Syllabus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SyllabusDAO {

    public void addModule(Syllabus syllabus, String moduleName) {

        String sql = "INSERT INTO syllabus_modules " +
                     "(course_code, module_name) " +
                     "VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, syllabus.getCourse().getCourseCode());
            statement.setString(2, moduleName);

            statement.executeUpdate();

            System.out.println("Syllabus module added successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add syllabus module.");
            e.printStackTrace();
        }
    }

    public void displaySyllabus(String courseCode) {

        String sql = "SELECT * FROM syllabus_modules " +
                     "WHERE course_code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, courseCode);

            try (ResultSet resultSet = statement.executeQuery()) {

                System.out.println("\n===== SYLLABUS FROM DATABASE =====");
                System.out.println("Course Code: " + courseCode);

                int count = 1;

                while (resultSet.next()) {

                    System.out.println(
                            count + ". " +
                            resultSet.getString("module_name")
                    );

                    count++;
                }

                if (count == 1) {
                    System.out.println("No syllabus modules found.");
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve syllabus.");
            e.printStackTrace();
        }
    }

    public void deleteModule(int moduleId) {

        String sql = "DELETE FROM syllabus_modules WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, moduleId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Syllabus module deleted successfully!");
            } else {
                System.out.println("Syllabus module not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete syllabus module.");
            e.printStackTrace();
        }
    }
}