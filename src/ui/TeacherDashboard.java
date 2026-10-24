
package ui;

import javax.swing.*;
import java.awt.*;

public class TeacherDashboard extends JFrame {

    public TeacherDashboard(String teacherId) {
        setTitle("SCMS - Teacher Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel heading = new JLabel("Teacher Dashboard");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel welcomeLabel =
                new JLabel("Welcome, Teacher ID: " + teacherId);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.add(heading);
        headerPanel.add(welcomeLabel);

        String[] sections = {
            "Manage Students",
            "Manage Attendance",
            "Manage Marks",
            "Manage Assignments",
            "Manage Class Sessions",
            "Logout"
        };

        JPanel cardsPanel = new JPanel(new GridLayout(3, 2, 15, 15));

        for (String section : sections) {
            JButton button = new JButton(section);
            button.setFont(new Font("SansSerif", Font.PLAIN, 16));

            if (section.equals("Logout")) {
                button.addActionListener(e -> {
                    dispose();
                    new LoginFrame().setVisible(true);
                });
            } else if (section.equals("Manage Students")) {
    button.addActionListener(e -> {
        JFrame studentsFrame = new JFrame("SCMS - Manage Students");
        studentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentsFrame.setSize(950, 500);
        studentsFrame.setLocationRelativeTo(this);
        studentsFrame.add(new TeacherStudentsPanel());
        studentsFrame.setVisible(true);
    });

    
} else if (section.equals("Manage Attendance")) {
    button.addActionListener(e -> {
        JFrame attendanceFrame =
                new JFrame("SCMS - Manage Attendance");

        attendanceFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);
        attendanceFrame.setSize(1100, 550);
        attendanceFrame.setLocationRelativeTo(this);
        attendanceFrame.add(new TeacherAttendancePanel());
        attendanceFrame.setVisible(true);
    });

} else if (section.equals("Manage Marks")) {
    button.addActionListener(e -> {
        JFrame marksFrame = new JFrame("SCMS - Manage Marks");
        marksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        marksFrame.setSize(1100, 550);
        marksFrame.setLocationRelativeTo(this);
        marksFrame.add(new TeacherMarksPanel());
        marksFrame.setVisible(true);
    });

} else if (section.equals("Manage Assignments")) {
    button.addActionListener(e -> {
        JFrame assignmentsFrame =
                new JFrame("SCMS - Manage Assignments");

        assignmentsFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);
        assignmentsFrame.setSize(1150, 600);
        assignmentsFrame.setLocationRelativeTo(this);
        assignmentsFrame.add(new TeacherAssignmentsPanel());
        assignmentsFrame.setVisible(true);
    });

} else if (section.equals("Manage Class Sessions")) {
    button.addActionListener(e -> {
        JFrame sessionsFrame =
                new JFrame("SCMS - Manage Class Sessions");

        sessionsFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);
        sessionsFrame.setSize(1150, 600);
        sessionsFrame.setLocationRelativeTo(this);
        sessionsFrame.add(new TeacherClassSessionsPanel());
        sessionsFrame.setVisible(true);
    });

} else {
    button.addActionListener(e ->
            JOptionPane.showMessageDialog(
                    this,
                    section + " will be connected next.",
                    section,
                    JOptionPane.INFORMATION_MESSAGE));
}

            cardsPanel.add(button);
        }

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}
