package service;

import model.Teacher;
import java.util.ArrayList;

public class TeacherService {

    private ArrayList<Teacher> teachers;

    public TeacherService() {
        teachers = new ArrayList<>();
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        System.out.println("Teacher added successfully.");
    }

    public void displayAllTeachers() {
        System.out.println("\n===== ALL TEACHERS =====");

        if (teachers.isEmpty()) {
            System.out.println("No teachers available.");
            return;
        }

        for (Teacher teacher : teachers) {
            teacher.displayDetails();
        }
    }

    public Teacher findTeacherById(String id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId().equals(id)) {
                return teacher;
            }
        }

        return null;
    }

    public boolean removeTeacher(String id) {
        Teacher teacher = findTeacherById(id);

        if (teacher != null) {
            teachers.remove(teacher);
            return true;
        }

        return false;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }
}