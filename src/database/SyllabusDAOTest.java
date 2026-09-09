package database;

import model.Course;
import model.Syllabus;

public class SyllabusDAOTest {

    public static void main(String[] args) {

        // First create a course in the database
        Course course = new Course(
                "CS301",
                "Data Structures",
                4
        );

        CourseDAO courseDAO = new CourseDAO();
        courseDAO.addCourse(course);

        // Create syllabus for the course
        Syllabus syllabus = new Syllabus(course);

        SyllabusDAO syllabusDAO = new SyllabusDAO();

        syllabusDAO.addModule(syllabus, "Introduction to Data Structures");
        syllabusDAO.addModule(syllabus, "Arrays and Linked Lists");
        syllabusDAO.addModule(syllabus, "Stacks and Queues");

        // Display modules from database
        syllabusDAO.displaySyllabus("CS301");
    }
}