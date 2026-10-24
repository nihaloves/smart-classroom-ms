
package ui;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentCoursesPanel extends JPanel {

    private final JTable coursesTable;
    private final DefaultTableModel coursesModel;

    public StudentCoursesPanel() {

        setLayout(new BorderLayout(10, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel heading = new JLabel("Courses & Syllabus");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(heading, BorderLayout.NORTH);

        String[] columns = {"Course Code", "Course Name", "Credits"};

        coursesModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        coursesTable = new JTable(coursesModel);
        coursesTable.setRowHeight(28);
        add(new JScrollPane(coursesTable), BorderLayout.CENTER);

        JButton viewSyllabusButton = new JButton("View Selected Syllabus");
        viewSyllabusButton.addActionListener(e -> showSyllabus());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(viewSyllabusButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadCourses();
    }

    private void loadCourses() {

        String sql = "SELECT course_code, course_name, credits " +
                     "FROM courses ORDER BY course_code";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                coursesModel.addRow(new Object[] {
                    resultSet.getString("course_code"),
                    resultSet.getString("course_name"),
                    resultSet.getInt("credits")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load courses. Check the database connection.",
                    "Courses Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void showSyllabus() {

        int selectedRow = coursesTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a course first."
            );
            return;
        }

        String courseCode = coursesModel.getValueAt(
                coursesTable.convertRowIndexToModel(selectedRow), 0
        ).toString();

        String sql = "SELECT module_name FROM syllabus_modules " +
                     "WHERE course_code = ? ORDER BY id";

        DefaultListModel<String> moduleModel = new DefaultListModel<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, courseCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    moduleModel.addElement(resultSet.getString("module_name"));
                }
            }

            if (moduleModel.isEmpty()) {
                moduleModel.addElement("No syllabus modules found.");
            }

            JList<String> moduleList = new JList<>(moduleModel);
            moduleList.setFont(new Font("SansSerif", Font.PLAIN, 14));

            JScrollPane scrollPane = new JScrollPane(moduleList);
            scrollPane.setPreferredSize(new Dimension(400, 250));

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Syllabus - " + courseCode,
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load syllabus.",
                    "Syllabus Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
