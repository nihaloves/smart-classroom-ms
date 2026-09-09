package database;

import model.Assignment;
import model.Course;

public class AssignmentDAOTest {

    public static void main(String[] args) {

        // Create a course first because assignment depends on it
        Course course = new Course(
                "CS301",
                "Data Structures",
                4
        );

        CourseDAO courseDAO = new CourseDAO();
        courseDAO.addCourse(course);

        // Create assignment
        Assignment assignment = new Assignment(
                "Linked List Assignment",
                "Implement a singly linked list in Java.",
                "2026-09-20",
                course
        );

        AssignmentDAO assignmentDAO = new AssignmentDAO();

        assignmentDAO.addAssignment(assignment);
        assignmentDAO.displayAssignments();
    }
}