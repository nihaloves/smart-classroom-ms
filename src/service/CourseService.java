package service;

import model.Course;
import java.util.ArrayList;

public class CourseService {

    private ArrayList<Course> courses;

    public CourseService() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Course added successfully.");
    }

    public void displayAllCourses() {
        System.out.println("\n===== ALL COURSES =====");

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course course : courses) {
            course.displayCourse();
        }
    }

    public Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }

        return null;
    }

    public boolean removeCourse(String courseCode) {
        Course course = findCourseByCode(courseCode);

        if (course != null) {
            courses.remove(course);
            return true;
        }

        return false;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}