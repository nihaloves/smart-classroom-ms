
package ui;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentClassesPanel extends JPanel {

    public StudentClassesPanel() {

        setLayout(new BorderLayout(10, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel heading = new JLabel("My Classes");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(heading, BorderLayout.NORTH);

        String[] columns = {
            "Course Code",
            "Teacher ID",
            "Room",
            "Date",
            "Start Time",
            "End Time"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(28);
        add(new JScrollPane(table), BorderLayout.CENTER);

        String sql = "SELECT course_code, teacher_id, room_number, " +
                     "session_date, start_time, end_time " +
                     "FROM class_sessions ORDER BY session_date, start_time";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                model.addRow(new Object[] {
                    resultSet.getString("course_code"),
                    resultSet.getString("teacher_id"),
                    resultSet.getString("room_number"),
                    resultSet.getDate("session_date"),
                    resultSet.getTime("start_time"),
                    resultSet.getTime("end_time")
                });
            }

            if (model.getRowCount() == 0) {
                add(new JLabel("No class sessions scheduled."),
                        BorderLayout.SOUTH);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load class sessions. Check the database connection.",
                    "Classes Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
