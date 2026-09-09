package database;

public class AttendanceDAOTest {

    public static void main(String[] args) {

        AttendanceDAO attendanceDAO = new AttendanceDAO();

        attendanceDAO.addAttendance(
                "TL25BTCS0214",
                "CS301",
                20,
                17
        );

        attendanceDAO.displayAttendance("TL25BTCS0214");
    }
}