package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClassSessionDAO {

    public void addClassSession(String courseCode,
                                 String teacherId,
                                 String roomNumber,
                                 String sessionDate,
                                 String startTime,
                                 String endTime) {

        String sql = "INSERT INTO class_sessions " +
                     "(course_code, teacher_id, room_number, session_date, start_time, end_time) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, courseCode);
            statement.setString(2, teacherId);
            statement.setString(3, roomNumber);
            statement.setString(4, sessionDate);
            statement.setString(5, startTime);
            statement.setString(6, endTime);

            statement.executeUpdate();

            System.out.println("Class session added to database successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add class session.");
            e.printStackTrace();
        }
    }

    public void displayClassSessions() {

        String sql = "SELECT * FROM class_sessions";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n===== CLASS SESSIONS FROM DATABASE =====");

            while (resultSet.next()) {

                System.out.println("Session ID: " +
                        resultSet.getInt("id"));

                System.out.println("Course Code: " +
                        resultSet.getString("course_code"));

                System.out.println("Teacher ID: " +
                        resultSet.getString("teacher_id"));

                System.out.println("Room Number: " +
                        resultSet.getString("room_number"));

                System.out.println("Date: " +
                        resultSet.getDate("session_date"));

                System.out.println("Start Time: " +
                        resultSet.getTime("start_time"));

                System.out.println("End Time: " +
                        resultSet.getTime("end_time"));

                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve class sessions.");
            e.printStackTrace();
        }
    }

    public void deleteClassSession(int sessionId) {

        String sql = "DELETE FROM class_sessions WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, sessionId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Class session deleted successfully!");
            } else {
                System.out.println("Class session not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete class session.");
            e.printStackTrace();
        }
    }
}