package database;

import model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherDAO {

    public void addTeacher(Teacher teacher) {

        String sql = "INSERT INTO teachers " +
                     "(id, name, email, department, specialization) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, teacher.getId());
            statement.setString(2, teacher.getName());
            statement.setString(3, teacher.getEmail());
            statement.setString(4, teacher.getDepartment());
            statement.setString(5, teacher.getSpecialization());

            statement.executeUpdate();

            System.out.println("Teacher added to database successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add teacher.");
            e.printStackTrace();
        }
    }

    public void displayTeachers() {

        String sql = "SELECT * FROM teachers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n===== TEACHERS FROM DATABASE =====");

            while (resultSet.next()) {

                System.out.println("ID: " +
                        resultSet.getString("id"));

                System.out.println("Name: " +
                        resultSet.getString("name"));

                System.out.println("Email: " +
                        resultSet.getString("email"));

                System.out.println("Department: " +
                        resultSet.getString("department"));

                System.out.println("Specialization: " +
                        resultSet.getString("specialization"));

                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve teachers.");
            e.printStackTrace();
        }
    }

    public void deleteTeacher(String teacherId) {

        String sql = "DELETE FROM teachers WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, teacherId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Teacher deleted successfully!");
            } else {
                System.out.println("Teacher not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete teacher.");
            e.printStackTrace();
        }
    }
}