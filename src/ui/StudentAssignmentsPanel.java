
package ui;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentAssignmentsPanel extends JPanel {

    public StudentAssignmentsPanel() {

        setLayout(new BorderLayout(10, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel heading = new JLabel("Assignments");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(heading, BorderLayout.NORTH);

        String[] columns = {
            "Title", "Description", "Due Date", "Course Code"
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

        String sql = "SELECT title, description, due_date, course_code " +
                     "FROM assignments ORDER BY due_date";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                model.addRow(new Object[] {
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getDate("due_date"),
                    resultSet.getString("course_code")
                });
            }

            if (model.getRowCount() == 0) {
                add(new JLabel("No assignments available."),
                        BorderLayout.SOUTH);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load assignments. Check the database connection.",
                    "Assignments Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
