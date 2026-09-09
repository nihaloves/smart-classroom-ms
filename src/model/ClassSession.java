package model;

public class ClassSession {

    private Course course;
    private Teacher teacher;
    private Classroom classroom;
    private String date;
    private String startTime;
    private String endTime;

    public ClassSession(Course course, Teacher teacher, Classroom classroom,
                        String date, String startTime, String endTime) {
        this.course = course;
        this.teacher = teacher;
        this.classroom = classroom;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Course getCourse() {
        return course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void displaySession() {
        System.out.println("\n===== CLASS SESSION =====");
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Name: " + course.getCourseName());
        System.out.println("Teacher: " + teacher.getName());
        System.out.println("Classroom: " + classroom.getRoomNumber());
        System.out.println("Building: " + classroom.getBuilding());
        System.out.println("Date: " + date);
        System.out.println("Time: " + startTime + " - " + endTime);
    }
}