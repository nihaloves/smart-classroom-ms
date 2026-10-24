
package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import service.LoginService;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton loginButton;
    private JLabel messageLabel;
    private LoginService loginService;

    public LoginFrame() {

        setTitle("SCMS - Smart Classroom Management System");
        setSize(450, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("SCMS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel subtitleLabel = new JLabel(
                "Smart Classroom Management System",
                SwingConstants.CENTER
        );

        usernameField = new JTextField(18);
        passwordField = new JPasswordField(18);
        roleBox = new JComboBox<>(new String[]{"STUDENT", "TEACHER"});
        loginButton = new JButton("Login");
        messageLabel = new JLabel(" ", SwingConstants.CENTER);

        loginService = new LoginService();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(subtitleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        panel.add(roleBox, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        gbc.gridy = 6;
        panel.add(messageLabel, gbc);

        add(panel);

        // Handle login button clicks
        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            char[] passwordChars = passwordField.getPassword();

            if (username.isEmpty() || passwordChars.length == 0) {
                Arrays.fill(passwordChars, '\0');
                messageLabel.setText("Enter username and password.");
                return;
            }

            String password = new String(passwordChars);
            Arrays.fill(passwordChars, '\0');

            String role = (String) roleBox.getSelectedItem();

            loginButton.setEnabled(false);
            messageLabel.setText("Checking credentials...");

            try {
                String userId = loginService.login(
                        username, password, role
                );

                if (userId != null) {
                    messageLabel.setText("Login successful!");

                    JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Welcome!\nRole: " + role
                         + "\nUser ID: " + userId
            );

                    if ("STUDENT".equals(role)) {
                        StudentDashboard dashboard =
                            new StudentDashboard(userId);

                            dashboard.setVisible(true);
                            dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                            LoginFrame.this,
                "Teacher dashboard is coming next."
        );
    }
}

            } catch (Exception ex) {
                messageLabel.setText("Unable to complete login.");
                ex.printStackTrace();

            } finally {
                loginButton.setEnabled(true);
                passwordField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
