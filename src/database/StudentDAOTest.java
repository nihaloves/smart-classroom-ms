package database;

import model.Student;

public class StudentDAOTest {

    public static void main(String[] args) {

        Student student = new Student(
                "S001",
                "Niharika",
                "niharika@example.com",
                "CSE",
                3
        );

        StudentDAO studentDAO = new StudentDAO();

        studentDAO.addStudent(student);

        studentDAO.displayStudents();
    }
}