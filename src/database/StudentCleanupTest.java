package database;

public class StudentCleanupTest {

    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();

        studentDAO.deleteStudent("S001");

        studentDAO.displayStudents();
    }
}