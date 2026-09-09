package database;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        userDAO.addUser(
                "teststudent",
                "test123",
                "STUDENT",
                "TL25BTCS0214"
        );

        boolean loginResult = userDAO.validateLogin(
                "teststudent",
                "test123",
                "STUDENT"
        );

        System.out.println("Login result: " + loginResult);

        String userId = userDAO.getUserId(
                "teststudent",
                "test123",
                "STUDENT"
        );

        System.out.println("User ID: " + userId);

        userDAO.deleteUser("teststudent");
    }
}