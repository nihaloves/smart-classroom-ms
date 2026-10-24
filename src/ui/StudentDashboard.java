
package ui;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private final String studentId;

    public StudentDashboard(String studentId) {
        this.studentId = studentId;

        setTitle("SCMS - Student Dashboard");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(650, 450));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel heading = new JLabel("Student Dashboard");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel welcomeLabel = new JLabel(
                "Welcome! Student ID: " + studentId
        );
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel header = new JPanel(new BorderLayout(5, 8));
        header.add(heading, BorderLayout.NORTH);
        header.add(welcomeLabel, BorderLayout.SOUTH);

        JPanel cardsPanel = new JPanel(new GridLayout(3, 2, 15, 15));

        String[] sections = {
                "My Profile",
                "My Attendance",
                "My Marks",
                "Assignments",
                "My Classes",
                "Courses & Syllabus"
        };

        
for (String section : sections) {
    JButton button = new JButton(section);
    button.setFont(new Font("SansSerif", Font.PLAIN, 16));

    if (section.equals("My Profile")) {
        button.addActionListener(e -> {
            JFrame profileFrame = new JFrame("SCMS - My Profile");
            profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            profileFrame.setSize(600, 400);
            profileFrame.setLocationRelativeTo(this);
            profileFrame.add(new StudentProfilePanel(studentId));
            profileFrame.setVisible(true);
        });
    }

    if (section.equals("My Attendance")) {
        button.addActionListener(e -> {
            JFrame attendanceFrame =
                    new JFrame("SCMS - My Attendance");

            attendanceFrame.setDefaultCloseOperation(
                    JFrame.DISPOSE_ON_CLOSE);
            attendanceFrame.setSize(750, 400);
            attendanceFrame.setLocationRelativeTo(this);

            attendanceFrame.add(
                    new StudentAttendancePanel(studentId));

            attendanceFrame.setVisible(true);
        });
    }
    
if (section.equals("My Marks")) {
    button.addActionListener(e -> {
        JFrame marksFrame = new JFrame("SCMS - My Marks");

        marksFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);
        marksFrame.setSize(750, 400);
        marksFrame.setLocationRelativeTo(this);

        marksFrame.add(new StudentMarksPanel(studentId));
        marksFrame.setVisible(true);
    });
}

if (section.equals("Assignments")) {
    button.addActionListener(e -> {
        JFrame assignmentsFrame =
                new JFrame("SCMS - Assignments");

        assignmentsFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);
        assignmentsFrame.setSize(850, 450);
        assignmentsFrame.setLocationRelativeTo(this);

        assignmentsFrame.add(new StudentAssignmentsPanel());
        assignmentsFrame.setVisible(true);
    });
}

if (section.equals("Courses & Syllabus")) {
    button.addActionListener(e -> {
        JFrame coursesFrame =
                new JFrame("SCMS - Courses & Syllabus");

        coursesFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);
        coursesFrame.setSize(800, 450);
        coursesFrame.setLocationRelativeTo(this);

        coursesFrame.add(new StudentCoursesPanel());
        coursesFrame.setVisible(true);
    });
}


if (section.equals("My Classes")) {
    button.addActionListener(e -> {
        JFrame classesFrame =
                new JFrame("SCMS - My Classes");

        classesFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);
        classesFrame.setSize(950, 450);
        classesFrame.setLocationRelativeTo(this);

        classesFrame.add(new StudentClassesPanel());
        classesFrame.setVisible(true);
    });
}


    cardsPanel.add(button);
}

        JLabel statusLabel = new JLabel(
                "Select a section to view your academic information."
        );

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentDashboard dashboard =
                    new StudentDashboard("TL25BTCS0214");

            dashboard.setVisible(true);
        });
    }
}
