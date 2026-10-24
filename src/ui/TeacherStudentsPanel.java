
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DatabaseConnection;

public class TeacherStudentsPanel extends JPanel {

    private final DefaultTableModel tableModel;

    public TeacherStudentsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("Manage Students");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));

        String[] columns = {
            "Student ID", "Name", "Email", "Department", "Semester"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.setAutoCreateRowSorter(true);

        JButton refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(e -> loadStudents());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(refreshButton);

        add(heading, BorderLayout.NORTH);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadStudents();
    }

    private void loadStudents() {
        tableModel.setRowCount(0);

        String sql = "SELECT id, name, email, department, semester " +
                     "FROM students ORDER BY name";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                tableModel.addRow(new Object[] {
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("department"),
                    resultSet.getInt("semester")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load students: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
