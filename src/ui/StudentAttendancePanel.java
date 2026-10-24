
package ui;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentAttendancePanel extends JPanel {

    public StudentAttendancePanel(String studentId) {

        setLayout(new BorderLayout(10, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel heading = new JLabel("My Attendance");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(heading, BorderLayout.NORTH);

        String[] columns = {
            "Course Code",
            "Total Classes",
            "Attended Classes",
            "Attendance (%)"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        String sql = "SELECT course_code, total_classes, attended_classes " +
                     "FROM attendance WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int total = resultSet.getInt("total_classes");
                    int attended = resultSet.getInt("attended_classes");

                    double percentage = total > 0
                            ? attended * 100.0 / total
                            : 0.0;

                    model.addRow(new Object[] {
                        resultSet.getString("course_code"),
                        total,
                        attended,
                        String.format("%.2f%%", percentage)
                    });
                }
            }

            if (model.getRowCount() == 0) {
                add(new JLabel("No attendance records found."),
                        BorderLayout.SOUTH);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load attendance. Check the database connection.",
                    "Attendance Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
