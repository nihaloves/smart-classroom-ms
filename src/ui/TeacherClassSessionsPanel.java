
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DatabaseConnection;

public class TeacherClassSessionsPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final JTextField courseCodeField;
    private final JTextField teacherIdField;
    private final JTextField roomNumberField;
    private final JTextField sessionDateField;
    private final JTextField startTimeField;
    private final JTextField endTimeField;

    public TeacherClassSessionsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("Manage Class Sessions");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));

        String[] columns = {
            "ID", "Course Code", "Teacher ID", "Room",
            "Date", "Start Time", "End Time"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable sessionsTable = new JTable(tableModel);
        sessionsTable.setRowHeight(24);
        sessionsTable.setAutoCreateRowSorter(true);

        courseCodeField = new JTextField(7);
        teacherIdField = new JTextField(7);
        roomNumberField = new JTextField(6);
        sessionDateField = new JTextField(9);
        startTimeField = new JTextField(5);
        endTimeField = new JTextField(5);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addField(formPanel, gbc, 0, 0, "Course Code:", courseCodeField);
        addField(formPanel, gbc, 2, 0, "Teacher ID:", teacherIdField);
        addField(formPanel, gbc, 4, 0, "Room:", roomNumberField);

        addField(formPanel, gbc, 0, 1, "Date (YYYY-MM-DD):", sessionDateField);
        addField(formPanel, gbc, 2, 1, "Start (HH:MM:SS):", startTimeField);
        addField(formPanel, gbc, 4, 1, "End (HH:MM:SS):", endTimeField);

        JButton saveButton = new JButton("Schedule Session");
        JButton refreshButton = new JButton("Refresh");
        JButton deleteButton = new JButton("Delete Selected");

        saveButton.addActionListener(e -> addSession());
        refreshButton.addActionListener(e -> loadSessions());
        deleteButton.addActionListener(e -> deleteSession(sessionsTable));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        JPanel northPanel = new JPanel(new BorderLayout(5, 10));
        northPanel.add(heading, BorderLayout.NORTH);
        northPanel.add(formPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(sessionsTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadSessions();
    }

    private void addField(JPanel panel, GridBagConstraints gbc,
                          int x, int y, String label, JTextField field) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = x + 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private void addSession() {
        String courseCode = courseCodeField.getText().trim();
        String teacherId = teacherIdField.getText().trim();
        String room = roomNumberField.getText().trim();
        String date = sessionDateField.getText().trim();
        String start = startTimeField.getText().trim();
        String end = endTimeField.getText().trim();

        if (courseCode.isEmpty() || teacherId.isEmpty()
                || room.isEmpty() || date.isEmpty()
                || start.isEmpty() || end.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.");
            return;
        }

        String sql = "INSERT INTO class_sessions " +
                "(course_code, teacher_id, room_number, session_date, " +
                "start_time, end_time) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, courseCode);
            statement.setString(2, teacherId);
            statement.setString(3, room);
            statement.setDate(4, java.sql.Date.valueOf(date));
            statement.setTime(5, java.sql.Time.valueOf(start));
            statement.setTime(6, java.sql.Time.valueOf(end));

            statement.executeUpdate();

            loadSessions();
            JOptionPane.showMessageDialog(this, "Class session scheduled.");

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Check date format YYYY-MM-DD and time format HH:MM:SS.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not schedule session: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSessions() {
        tableModel.setRowCount(0);

        String sql = "SELECT id, course_code, teacher_id, room_number, " +
                "session_date, start_time, end_time " +
                "FROM class_sessions ORDER BY session_date, start_time";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                tableModel.addRow(new Object[] {
                    resultSet.getInt("id"),
                    resultSet.getString("course_code"),
                    resultSet.getString("teacher_id"),
                    resultSet.getString("room_number"),
                    resultSet.getDate("session_date"),
                    resultSet.getTime("start_time"),
                    resultSet.getTime("end_time")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load sessions: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSession(JTable sessionsTable) {
        int selectedRow = sessionsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a class session first.");
            return;
        }

        int modelRow = sessionsTable.convertRowIndexToModel(selectedRow);
        int sessionId = Integer.parseInt(
                tableModel.getValueAt(modelRow, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete class session ID " + sessionId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM class_sessions WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, sessionId);
            statement.executeUpdate();

            loadSessions();
            JOptionPane.showMessageDialog(this, "Class session deleted.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete session: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
