
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DatabaseConnection;

public class TeacherAssignmentsPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final JTextField titleField;
    private final JTextField dueDateField;
    private final JTextField courseCodeField;
    private final JTextArea descriptionArea;

    public TeacherAssignmentsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("Manage Assignments");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));

        String[] columns = {
            "ID", "Title", "Description", "Due Date", "Course Code"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable assignmentTable = new JTable(tableModel);
        assignmentTable.setRowHeight(24);
        assignmentTable.setAutoCreateRowSorter(true);

        titleField = new JTextField(12);
        dueDateField = new JTextField(10);
        courseCodeField = new JTextField(8);
        descriptionArea = new JTextArea(3, 18);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Due Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 3;
        formPanel.add(dueDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Course Code:"), gbc);
        gbc.gridx = 1;
        formPanel.add(courseCodeField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(new JScrollPane(descriptionArea), gbc);

        JButton saveButton = new JButton("Add Assignment");
        JButton refreshButton = new JButton("Refresh");
        JButton deleteButton = new JButton("Delete Selected");

        saveButton.addActionListener(e -> addAssignment());
        refreshButton.addActionListener(e -> loadAssignments());
        deleteButton.addActionListener(e -> deleteAssignment(assignmentTable));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        JPanel northPanel = new JPanel(new BorderLayout(5, 10));
        northPanel.add(heading, BorderLayout.NORTH);
        northPanel.add(formPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(assignmentTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadAssignments();
    }

    private void addAssignment() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        String dueDate = dueDateField.getText().trim();
        String courseCode = courseCodeField.getText().trim();

        if (title.isEmpty() || description.isEmpty()
                || dueDate.isEmpty() || courseCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.");
            return;
        }

        String sql = "INSERT INTO assignments " +
                "(title, description, due_date, course_code) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title);
            statement.setString(2, description);
            statement.setDate(3, java.sql.Date.valueOf(dueDate));
            statement.setString(4, courseCode);

            statement.executeUpdate();

            loadAssignments();
            JOptionPane.showMessageDialog(this, "Assignment added.");

            titleField.setText("");
            descriptionArea.setText("");
            dueDateField.setText("");
            courseCodeField.setText("");

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this, "Enter the due date in YYYY-MM-DD format.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not add assignment: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAssignments() {
        tableModel.setRowCount(0);

        String sql = "SELECT id, title, description, due_date, course_code " +
                "FROM assignments ORDER BY due_date";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                tableModel.addRow(new Object[] {
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getDate("due_date"),
                    resultSet.getString("course_code")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load assignments: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAssignment(JTable assignmentTable) {
        int selectedRow = assignmentTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select an assignment first.");
            return;
        }

        int modelRow = assignmentTable.convertRowIndexToModel(selectedRow);
        int assignmentId = Integer.parseInt(
                tableModel.getValueAt(modelRow, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete assignment ID " + assignmentId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM assignments WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, assignmentId);
            statement.executeUpdate();

            loadAssignments();
            JOptionPane.showMessageDialog(this, "Assignment deleted.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete assignment: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
