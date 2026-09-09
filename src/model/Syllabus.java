package model;

import java.util.ArrayList;

public class Syllabus {

    private Course course;

    private ArrayList<String> modules;

    public Syllabus(Course course) {
        this.course = course;
        this.modules = new ArrayList<>();
    }

    public Course getCourse() {
        return course;
    }

    public ArrayList<String> getModules() {
        return modules;
    }

    public void addModule(String module) {
        modules.add(module);
    }

    public void displaySyllabus() {
        System.out.println("\n===== COURSE SYLLABUS =====");
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Name: " + course.getCourseName());

        System.out.println("\nModules:");

        if (modules.isEmpty()) {
            System.out.println("No modules added.");
            return;
        }

        for (int i = 0; i < modules.size(); i++) {
            System.out.println((i + 1) + ". " + modules.get(i));
        }
    }
}