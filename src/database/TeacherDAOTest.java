package database;

import model.Teacher;

public class TeacherDAOTest {

    public static void main(String[] args) {

        Teacher teacher = new Teacher(
                "T001",
                "Anil Kumar",
                "t001@vidyaacademy.ac.in",
                "CSE",
                "Data Structures"
        );

        TeacherDAO teacherDAO = new TeacherDAO();

        teacherDAO.addTeacher(teacher);
        teacherDAO.displayTeachers();
    }
}