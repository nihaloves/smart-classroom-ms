package service;

import model.ClassSession;
import model.Course;
import model.Teacher;
import java.util.ArrayList;

public class ClassSessionService {

    private ArrayList<ClassSession> sessions;

    public ClassSessionService() {
        sessions = new ArrayList<>();
    }

    public void addSession(ClassSession session) {
        sessions.add(session);
        System.out.println("Class session added successfully.");
    }

    public void displayAllSessions() {
        System.out.println("\n===== ALL CLASS SESSIONS =====");

        if (sessions.isEmpty()) {
            System.out.println("No class sessions available.");
            return;
        }

        for (ClassSession session : sessions) {
            session.displaySession();
        }
    }

    public ArrayList<ClassSession> getSessions() {
        return sessions;
    }
}