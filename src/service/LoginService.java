package service;

import database.UserDAO;

public class LoginService {

    private UserDAO userDAO;

    public LoginService() {
        userDAO = new UserDAO();
    }

    public String login(String username,
                         String password,
                         String role) {

        boolean valid = userDAO.validateLogin(
                username,
                password,
                role
        );

        if (valid) {
            return userDAO.getUserId(
                    username,
                    password,
                    role
            );
        }

        return null;
    }
}