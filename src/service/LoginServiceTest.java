package service;

import database.UserDAO;

public class LoginServiceTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // Create temporary test user
        userDAO.addUser(
                "teststudent",
                "test123",
                "STUDENT",
                "TL25BTCS0214"
        );

        // Test LoginService
        LoginService loginService = new LoginService();

        String userId = loginService.login(
                "teststudent",
                "test123",
                "STUDENT"
        );

        if (userId != null) {
            System.out.println("LoginService test successful!");
            System.out.println("Logged-in User ID: " + userId);
        } else {
            System.out.println("LoginService test failed.");
        }

        // Clean up test user
        userDAO.deleteUser("teststudent");
    }
}