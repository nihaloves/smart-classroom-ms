package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MarksDAO {

    public void addMarks(String studentId,
                         String courseCode,
                         double marksObtained,
                         double maximumMarks) {

        String sql = "INSERT INTO marks " +
                     "(student_id, course_code, marks_obtained, maximum_marks) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);
            statement.setString(2, courseCode);
            statement.setDouble(3, marksObtained);
            statement.setDouble(4, maximumMarks);

            statement.executeUpdate();

            System.out.println("Marks added to database successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add marks.");
            e.printStackTrace();
        }
    }

    public void displayMarks(String studentId) {

        String sql = "SELECT * FROM marks WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                System.out.println("\n===== MARKS FROM DATABASE =====");

                while (resultSet.next()) {

                    double obtained =
                            resultSet.getDouble("marks_obtained");

                    double maximum =
                            resultSet.getDouble("maximum_marks");

                    double percentage = 0.0;

                    if (maximum > 0) {
                        percentage = (obtained * 100.0) / maximum;
                    }

                    System.out.println("Student ID: " +
                            resultSet.getString("student_id"));

                    System.out.println("Course Code: " +
                            resultSet.getString("course_code"));

                    System.out.println("Marks: " +
                            obtained + " / " + maximum);

                    System.out.printf(
                            "Percentage: %.2f%%%n",
                            percentage
                    );

                    System.out.println("----------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve marks.");
            e.printStackTrace();
        }
    }

    public void deleteMarks(String studentId,
                             String courseCode) {

        String sql = "DELETE FROM marks " +
                     "WHERE student_id = ? AND course_code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);
            statement.setString(2, courseCode);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Marks deleted successfully!");
            } else {
                System.out.println("Marks record not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete marks.");
            e.printStackTrace();
        }
    }
}