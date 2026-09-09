package database;

public class ClassSessionDAOTest {

    public static void main(String[] args) {

        ClassSessionDAO classSessionDAO = new ClassSessionDAO();

        classSessionDAO.addClassSession(
                "CS301",
                "T001",
                "C101",
                "2026-09-08",
                "09:00:00",
                "10:00:00"
        );

        classSessionDAO.displayClassSessions();
    }
}