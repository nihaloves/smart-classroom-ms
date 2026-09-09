package database;

import model.Course;

public class CourseDAOTest {

    public static void main(String[] args) {

        Course course = new Course(
                "CS301",
                "Data Structures",
                4
        );

        CourseDAO courseDAO = new CourseDAO();

        courseDAO.addCourse(course);
        courseDAO.displayCourses();
    }
}