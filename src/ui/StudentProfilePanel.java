
package ui;

import database.StudentDAO;
import model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentProfilePanel extends JPanel {

    public StudentProfilePanel(String studentId) {

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel heading = new JLabel("My Profile");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));

        add(heading, BorderLayout.NORTH);

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.getStudentById(studentId);

        if (student == null) {
            JLabel errorLabel = new JLabel(
                    "Student profile not found or database unavailable."
            );
            errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
            add(errorLabel, BorderLayout.CENTER);
            return;
        }

        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        detailsPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 0, 20, 0)
        );

        addDetail(detailsPanel, "Student ID", student.getId());
        addDetail(detailsPanel, "Name", student.getName());
        addDetail(detailsPanel, "Email", student.getEmail());
        addDetail(detailsPanel, "Department", student.getDepartment());
        addDetail(detailsPanel, "Semester",
                String.valueOf(student.getSemester()));

        add(detailsPanel, BorderLayout.CENTER);
    }

    private void addDetail(
            JPanel panel, String label, String value) {

        JPanel row = new JPanel(new BorderLayout(15, 5));

        JLabel nameLabel = new JLabel(label);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(
                value == null ? "Not available" : value
        );
        valueLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        row.add(nameLabel, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.CENTER);

        panel.add(row);
    }
}
