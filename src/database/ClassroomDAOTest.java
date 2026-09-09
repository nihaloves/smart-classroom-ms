package database;

import model.Classroom;

public class ClassroomDAOTest {

    public static void main(String[] args) {

        Classroom classroom = new Classroom(
                "C101",
                60,
                "Main Block"
        );

        ClassroomDAO classroomDAO = new ClassroomDAO();

        classroomDAO.addClassroom(classroom);
        classroomDAO.displayClassrooms();
    }
}