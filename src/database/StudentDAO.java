package database;

import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    public void addStudent(Student student) {

        String sql = "INSERT INTO students " +
                     "(id, name, email, department, semester) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getDepartment());
            statement.setInt(5, student.getSemester());

            statement.executeUpdate();

            System.out.println("Student added to database successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add student.");
            e.printStackTrace();
        }
    }

    public void displayStudents() {

        String sql = "SELECT * FROM students";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n===== STUDENTS FROM DATABASE =====");

            while (resultSet.next()) {

                System.out.println("ID: " + resultSet.getString("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Department: " + resultSet.getString("department"));
                System.out.println("Semester: " + resultSet.getInt("semester"));
                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve students.");
            e.printStackTrace();
        }
    }
    public void deleteStudent(String studentId) {

    String sql = "DELETE FROM students WHERE id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, studentId);

        int rows = statement.executeUpdate();

        if (rows > 0) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found.");
        }

    } catch (Exception e) {
        System.out.println("Failed to delete student.");
        e.printStackTrace();
    }
}

}

