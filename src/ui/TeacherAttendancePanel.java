
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DatabaseConnection;

public class TeacherAttendancePanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final JTextField studentIdField;
    private final JTextField courseCodeField;
    private final JTextField totalClassesField;
    private final JTextField attendedClassesField;

    public TeacherAttendancePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("Manage Attendance");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));

        String[] columns = {
            "Student ID", "Course Code", "Total Classes",
            "Attended Classes", "Attendance %"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable attendanceTable = new JTable(tableModel);
        attendanceTable.setRowHeight(24);
        attendanceTable.setAutoCreateRowSorter(true);

        studentIdField = new JTextField(10);
        courseCodeField = new JTextField(8);
        totalClassesField = new JTextField(5);
        attendedClassesField = new JTextField(5);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(studentIdField);
        formPanel.add(new JLabel("Course Code:"));
        formPanel.add(courseCodeField);
        formPanel.add(new JLabel("Total:"));
        formPanel.add(totalClassesField);
        formPanel.add(new JLabel("Attended:"));
        formPanel.add(attendedClassesField);

        JButton saveButton = new JButton("Save Attendance");
        JButton refreshButton = new JButton("Refresh");
        JButton deleteButton = new JButton("Delete Selected");

        saveButton.addActionListener(e -> saveAttendance());
        refreshButton.addActionListener(e -> loadAttendance());
        deleteButton.addActionListener(e -> deleteAttendance(attendanceTable));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        JPanel northPanel = new JPanel(new BorderLayout(5, 10));
        northPanel.add(heading, BorderLayout.NORTH);
        northPanel.add(formPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(attendanceTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadAttendance();
    }

    private void saveAttendance() {
        String studentId = studentIdField.getText().trim();
        String courseCode = courseCodeField.getText().trim();

        if (studentId.isEmpty() || courseCode.isEmpty()
                || totalClassesField.getText().trim().isEmpty()
                || attendedClassesField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.");
            return;
        }

        try {
            int total = Integer.parseInt(totalClassesField.getText().trim());
            int attended = Integer.parseInt(attendedClassesField.getText().trim());

            if (total <= 0 || attended < 0 || attended > total) {
                JOptionPane.showMessageDialog(
                        this,
                        "Total must be positive, and attended must be between 0 and total."
                );
                return;
            }

            String sql = "INSERT INTO attendance " +
                    "(student_id, course_code, total_classes, attended_classes) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE total_classes = VALUES(total_classes), " +
                    "attended_classes = VALUES(attended_classes)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, studentId);
                statement.setString(2, courseCode);
                statement.setInt(3, total);
                statement.setInt(4, attended);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Attendance saved.");
                loadAttendance();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid whole numbers.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not save attendance: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadAttendance() {
        tableModel.setRowCount(0);

        String sql = "SELECT student_id, course_code, total_classes, " +
                "attended_classes FROM attendance ORDER BY student_id, course_code";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int total = resultSet.getInt("total_classes");
                int attended = resultSet.getInt("attended_classes");
                double percentage = total > 0 ? attended * 100.0 / total : 0.0;

                tableModel.addRow(new Object[] {
                    resultSet.getString("student_id"),
                    resultSet.getString("course_code"),
                    total,
                    attended,
                    String.format("%.2f%%", percentage)
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load attendance: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteAttendance(JTable attendanceTable) {
        int selectedRow = attendanceTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an attendance record first.");
            return;
        }

        int modelRow = attendanceTable.convertRowIndexToModel(selectedRow);
        String studentId = tableModel.getValueAt(modelRow, 0).toString();
        String courseCode = tableModel.getValueAt(modelRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete attendance for " + studentId + " in " + courseCode + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM attendance WHERE student_id = ? AND course_code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);
            statement.setString(2, courseCode);
            statement.executeUpdate();

            loadAttendance();
            JOptionPane.showMessageDialog(this, "Attendance record deleted.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete attendance: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
