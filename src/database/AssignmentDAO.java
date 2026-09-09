package database;

import model.Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AssignmentDAO {

    public void addAssignment(Assignment assignment) {

        String sql = "INSERT INTO assignments " +
                     "(title, description, due_date, course_code) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, assignment.getTitle());
            statement.setString(2, assignment.getDescription());
            statement.setString(3, assignment.getDueDate());
            statement.setString(4, assignment.getCourse().getCourseCode());

            statement.executeUpdate();

            System.out.println("Assignment added to database successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add assignment.");
            e.printStackTrace();
        }
    }

    public void displayAssignments() {

        String sql = "SELECT * FROM assignments";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n===== ASSIGNMENTS FROM DATABASE =====");

            while (resultSet.next()) {

                System.out.println("ID: " +
                        resultSet.getInt("id"));

                System.out.println("Title: " +
                        resultSet.getString("title"));

                System.out.println("Description: " +
                        resultSet.getString("description"));

                System.out.println("Due Date: " +
                        resultSet.getDate("due_date"));

                System.out.println("Course Code: " +
                        resultSet.getString("course_code"));

                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve assignments.");
            e.printStackTrace();
        }
    }

    public void deleteAssignment(int assignmentId) {

        String sql = "DELETE FROM assignments WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, assignmentId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Assignment deleted successfully!");
            } else {
                System.out.println("Assignment not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete assignment.");
            e.printStackTrace();
        }
    }
}