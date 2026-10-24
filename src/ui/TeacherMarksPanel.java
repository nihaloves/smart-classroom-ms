
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DatabaseConnection;

public class TeacherMarksPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final JTextField studentIdField;
    private final JTextField courseCodeField;
    private final JTextField marksField;
    private final JTextField maximumMarksField;

    public TeacherMarksPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("Manage Marks");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));

        String[] columns = {
            "Student ID", "Course Code", "Marks Obtained",
            "Maximum Marks", "Percentage"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable marksTable = new JTable(tableModel);
        marksTable.setRowHeight(24);
        marksTable.setAutoCreateRowSorter(true);

        studentIdField = new JTextField(9);
        courseCodeField = new JTextField(8);
        marksField = new JTextField(5);
        maximumMarksField = new JTextField(5);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(studentIdField);
        formPanel.add(new JLabel("Course Code:"));
        formPanel.add(courseCodeField);
        formPanel.add(new JLabel("Marks:"));
        formPanel.add(marksField);
        formPanel.add(new JLabel("Maximum:"));
        formPanel.add(maximumMarksField);

        JButton saveButton = new JButton("Save Marks");
        JButton refreshButton = new JButton("Refresh");
        JButton deleteButton = new JButton("Delete Selected");

        saveButton.addActionListener(e -> saveMarks());
        refreshButton.addActionListener(e -> loadMarks());
        deleteButton.addActionListener(e -> deleteMarks(marksTable));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        JPanel northPanel = new JPanel(new BorderLayout(5, 10));
        northPanel.add(heading, BorderLayout.NORTH);
        northPanel.add(formPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(marksTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadMarks();
    }

    private void saveMarks() {
        String studentId = studentIdField.getText().trim();
        String courseCode = courseCodeField.getText().trim();

        if (studentId.isEmpty() || courseCode.isEmpty()
                || marksField.getText().trim().isEmpty()
                || maximumMarksField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.");
            return;
        }

        try {
            double marks = Double.parseDouble(marksField.getText().trim());
            double maximum = Double.parseDouble(
                    maximumMarksField.getText().trim());

            if (!Double.isFinite(marks) || !Double.isFinite(maximum)
                    || maximum <= 0 || marks < 0 || marks > maximum) {
                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be between 0 and the maximum, which must be positive."
                );
                return;
            }

            String sql = "INSERT INTO marks " +
                    "(student_id, course_code, marks_obtained, maximum_marks) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "marks_obtained = VALUES(marks_obtained), " +
                    "maximum_marks = VALUES(maximum_marks)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, studentId);
                statement.setString(2, courseCode);
                statement.setDouble(3, marks);
                statement.setDouble(4, maximum);
                statement.executeUpdate();

                loadMarks();
                JOptionPane.showMessageDialog(this, "Marks saved.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Enter valid numeric marks.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not save marks: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadMarks() {
        tableModel.setRowCount(0);

        String sql = "SELECT student_id, course_code, marks_obtained, " +
                "maximum_marks FROM marks ORDER BY student_id, course_code";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                double marks = resultSet.getDouble("marks_obtained");
                double maximum = resultSet.getDouble("maximum_marks");
                double percentage = maximum > 0
                        ? marks * 100.0 / maximum : 0.0;

                tableModel.addRow(new Object[] {
                    resultSet.getString("student_id"),
                    resultSet.getString("course_code"),
                    marks,
                    maximum,
                    String.format("%.2f%%", percentage)
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load marks: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMarks(JTable marksTable) {
        int selectedRow = marksTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a marks record first.");
            return;
        }

        int modelRow = marksTable.convertRowIndexToModel(selectedRow);
        String studentId = tableModel.getValueAt(modelRow, 0).toString();
        String courseCode = tableModel.getValueAt(modelRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete marks for " + studentId + " in " + courseCode + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM marks WHERE student_id = ? AND course_code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);
            statement.setString(2, courseCode);
            statement.executeUpdate();

            loadMarks();
            JOptionPane.showMessageDialog(this, "Marks record deleted.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete marks: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
