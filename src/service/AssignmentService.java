package service;

import model.Assignment;
import java.util.ArrayList;

public class AssignmentService {

    private ArrayList<Assignment> assignments;

    public AssignmentService() {
        assignments = new ArrayList<>();
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        System.out.println("Assignment added successfully.");
    }

    public void displayAllAssignments() {
        System.out.println("\n===== ALL ASSIGNMENTS =====");

        if (assignments.isEmpty()) {
            System.out.println("No assignments available.");
            return;
        }

        for (Assignment assignment : assignments) {
            assignment.displayAssignment();
        }
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }
}