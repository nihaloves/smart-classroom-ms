package model;

public class Attendance {

    private Student student;
    private Course course;
    private int totalClasses;
    private int attendedClasses;

    public Attendance(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.totalClasses = 0;
        this.attendedClasses = 0;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public void markAttendance(boolean present) {
        totalClasses++;

        if (present) {
            attendedClasses++;
        }
    }

    public double getAttendancePercentage() {
        if (totalClasses == 0) {
            return 0.0;
        }

        return (attendedClasses * 100.0) / totalClasses;
    }

    public void displayAttendance() {
        System.out.println("\n===== ATTENDANCE =====");
        System.out.println("Student: " + student.getName());
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Name: " + course.getCourseName());
        System.out.println("Total Classes: " + totalClasses);
        System.out.println("Attended Classes: " + attendedClasses);
        System.out.printf("Attendance: %.2f%%%n", getAttendancePercentage());
    }
}