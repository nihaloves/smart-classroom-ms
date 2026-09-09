package database;

import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseDAO {

    public void addCourse(Course course) {

        String sql = "INSERT INTO courses " +
                     "(course_code, course_name, credits) " +
                     "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getCourseCode());
            statement.setString(2, course.getCourseName());
            statement.setInt(3, course.getCredits());

            statement.executeUpdate();

            System.out.println("Course added to database successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add course.");
            e.printStackTrace();
        }
    }

    public void displayCourses() {

        String sql = "SELECT * FROM courses";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n===== COURSES FROM DATABASE =====");

            while (resultSet.next()) {

                System.out.println("Course Code: " +
                        resultSet.getString("course_code"));

                System.out.println("Course Name: " +
                        resultSet.getString("course_name"));

                System.out.println("Credits: " +
                        resultSet.getInt("credits"));

                System.out.println("----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve courses.");
            e.printStackTrace();
        }
    }

    public void deleteCourse(String courseCode) {

        String sql = "DELETE FROM courses WHERE course_code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, courseCode);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Course deleted successfully!");
            } else {
                System.out.println("Course not found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to delete course.");
            e.printStackTrace();
        }
    }
}