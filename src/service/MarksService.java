package service;

import model.Marks;
import model.Student;
import model.Course;
import java.util.ArrayList;

public class MarksService {

    private ArrayList<Marks> marksRecords;

    public MarksService() {
        marksRecords = new ArrayList<>();
    }

    public void addMarks(Marks marks) {
        marksRecords.add(marks);
        System.out.println("Marks added successfully.");
    }

    public void displayAllMarks() {
        System.out.println("\n===== ALL MARKS =====");

        if (marksRecords.isEmpty()) {
            System.out.println("No marks records available.");
            return;
        }

        for (Marks marks : marksRecords) {
            marks.displayMarks();
        }
    }

    public Marks findMarks(Student student, Course course) {
        for (Marks marks : marksRecords) {
            if (marks.getStudent().getId().equals(student.getId())
                    && marks.getCourse().getCourseCode().equals(course.getCourseCode())) {
                return marks;
            }
        }

        return null;
    }

    public ArrayList<Marks> getMarksRecords() {
        return marksRecords;
    }
}