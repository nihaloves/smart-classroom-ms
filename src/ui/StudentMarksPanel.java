
package ui;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentMarksPanel extends JPanel {

    public StudentMarksPanel(String studentId) {

        setLayout(new BorderLayout(10, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel heading = new JLabel("My Marks");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(heading, BorderLayout.NORTH);

        String[] columns = {
            "Course Code",
            "Marks Obtained",
            "Maximum Marks",
            "Percentage"
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

        String sql = "SELECT course_code, marks_obtained, maximum_marks " +
                     "FROM marks WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    double obtained =
                            resultSet.getDouble("marks_obtained");

                    double maximum =
                            resultSet.getDouble("maximum_marks");

                    double percentage = maximum > 0
                            ? obtained * 100.0 / maximum
                            : 0.0;

                    model.addRow(new Object[] {
                        resultSet.getString("course_code"),
                        obtained,
                        maximum,
                        String.format("%.2f%%", percentage)
                    });
                }
            }

            if (model.getRowCount() == 0) {
                add(new JLabel("No marks records found."),
                        BorderLayout.SOUTH);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load marks. Check the database connection.",
                    "Marks Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
