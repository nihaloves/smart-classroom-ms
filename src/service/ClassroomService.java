package service;

import model.Classroom;
import java.util.ArrayList;

public class ClassroomService {

    private ArrayList<Classroom> classrooms;

    public ClassroomService() {
        classrooms = new ArrayList<>();
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
        System.out.println("Classroom added successfully.");
    }

    public void displayAllClassrooms() {
        System.out.println("\n===== ALL CLASSROOMS =====");

        if (classrooms.isEmpty()) {
            System.out.println("No classrooms available.");
            return;
        }

        for (Classroom classroom : classrooms) {
            classroom.displayClassroom();
        }
    }

    public Classroom findClassroomByRoomNumber(String roomNumber) {
        for (Classroom classroom : classrooms) {
            if (classroom.getRoomNumber().equals(roomNumber)) {
                return classroom;
            }
        }

        return null;
    }

    public boolean removeClassroom(String roomNumber) {
        Classroom classroom = findClassroomByRoomNumber(roomNumber);

        if (classroom != null) {
            classrooms.remove(classroom);
            return true;
        }

        return false;
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }
}