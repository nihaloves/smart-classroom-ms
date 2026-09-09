package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AttendanceDAO {

    public void addAttendance(String studentId,
                              String courseCode,
                              int totalClasses,
                              int attendedClasses) {

        String sql = "INSERT INTO attendance " +
                     "(student_id, course_code, total_classes, attended_classes) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);
            statement.setString(2, courseCode);
            statement.setInt(3, totalClasses);
            statement.setInt(4, attendedClasses);

            statement.executeUpdate();

            System.out.println("Attendance added to database successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add attendance.");
            e.printStackTrace();
        }
    }

    public void displayAttendance(String studentId) {

        String sql = "SELECT * FROM attendance " +
                     "WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                System.out.println("\n===== ATTENDANCE FROM DATABASE =====");

                while (resultSet.next()) {

                    System.out.println("Student ID: " +
                            resultSet.getString("student_id"));

                    System.out.println("Course Code: " +
                            resultSet.getString("course_code"));

                    System.out.println("Total Classes: " +
                            resultSet.getInt("total_classes"));

                    System.out.println("Attended Classes: " +
                            resultSet.getInt("attended_classes"));

                    int total = resultSet.getInt("total_classes");
                    int attended = resultSet.getInt("attended_classes");

                    double percentage = 0.0;

                    if (total > 0) {
                        percentage = (attended * 100.0) / total;
                    }

                    System.out.printf(
                            "Attendance: %.2f%%%n",
                            percentage
                    );

                    System.out.println("----------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve attendance.");
            e.printStackTrace();
        }
    }

    public void deleteAttendance(String studentId,
                                  String courseCode) {

        String sql = "DELETE FROM attendance " +
                     "WHERE student_id = ? AND course_code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);
            statement.setString(2, courseCode);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Attendance deleted successfully!");
            } else {
                System.out.println("Attendance record not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete attendance.");
            e.printStackTrace();
        }
    }
}