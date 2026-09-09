package service;

import model.Attendance;
import model.Student;
import model.Course;
import java.util.ArrayList;

public class AttendanceService {

    private ArrayList<Attendance> attendanceRecords;

    public AttendanceService() {
        attendanceRecords = new ArrayList<>();
    }

    public void addAttendanceRecord(Attendance attendance) {
        attendanceRecords.add(attendance);
        System.out.println("Attendance record added successfully.");
    }

    public void displayAllAttendance() {
        System.out.println("\n===== ALL ATTENDANCE RECORDS =====");

        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records available.");
            return;
        }

        for (Attendance attendance : attendanceRecords) {
            attendance.displayAttendance();
        }
    }

    public Attendance findAttendance(Student student, Course course) {
        for (Attendance attendance : attendanceRecords) {
            if (attendance.getStudent().getId().equals(student.getId())
                    && attendance.getCourse().getCourseCode().equals(course.getCourseCode())) {
                return attendance;
            }
        }

        return null;
    }

    public ArrayList<Attendance> getAttendanceRecords() {
        return attendanceRecords;
    }
}