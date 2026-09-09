package model;

public class Marks {

    private Student student;
    private Course course;
    private double marksObtained;
    private double maximumMarks;

    public Marks(Student student, Course course, double marksObtained, double maximumMarks) {
        this.student = student;
        this.course = course;
        this.marksObtained = marksObtained;
        this.maximumMarks = maximumMarks;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public double getMaximumMarks() {
        return maximumMarks;
    }

    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public double getPercentage() {
        if (maximumMarks == 0) {
            return 0.0;
        }

        return (marksObtained * 100.0) / maximumMarks;
    }

    public void displayMarks() {
        System.out.println("\n===== MARKS =====");
        System.out.println("Student: " + student.getName());
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Name: " + course.getCourseName());
        System.out.println("Marks: " + marksObtained + " / " + maximumMarks);
        System.out.printf("Percentage: %.2f%%%n", getPercentage());
    }
}