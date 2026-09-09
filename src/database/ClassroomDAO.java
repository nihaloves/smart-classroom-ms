package database;

import model.Classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClassroomDAO {

    public void addClassroom(Classroom classroom) {

        String sql = "INSERT INTO classrooms " +
                     "(room_number, capacity, building) " +
                     "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, classroom.getRoomNumber());
            statement.setInt(2, classroom.getCapacity());
            statement.setString(3, classroom.getBuilding());

            statement.executeUpdate();

            System.out.println("Classroom added to database successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add classroom.");
            e.printStackTrace();
        }
    }

    public void displayClassrooms() {

        String sql = "SELECT * FROM classrooms";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n===== CLASSROOMS FROM DATABASE =====");

            while (resultSet.next()) {

                System.out.println("Room Number: " +
                        resultSet.getString("room_number"));

                System.out.println("Capacity: " +
                        resultSet.getInt("capacity"));

                System.out.println("Building: " +
                        resultSet.getString("building"));

                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve classrooms.");
            e.printStackTrace();
        }
    }

    public void deleteClassroom(String roomNumber) {

        String sql = "DELETE FROM classrooms WHERE room_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, roomNumber);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Classroom deleted successfully!");
            } else {
                System.out.println("Classroom not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete classroom.");
            e.printStackTrace();
        }
    }
}