
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
