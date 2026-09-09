package database;

public class MarksDAOTest {

    public static void main(String[] args) {

        // Create the course required by the foreign key
        CourseDAO courseDAO = new CourseDAO();

        model.Course course = new model.Course(
                "CS301",
                "Data Structures",
                4
        );

        courseDAO.addCourse(course);

        // Add marks for an existing student
        MarksDAO marksDAO = new MarksDAO();

        marksDAO.addMarks(
                "TL25BTCS0214",
                "CS301",
                42,
                50
        );

        // Display marks
        marksDAO.displayMarks("TL25BTCS0214");
    }
}