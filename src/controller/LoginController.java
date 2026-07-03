package controller;

import dao.UserDAO;
import model.User;

/**
 * Sits between the login screen and the UserDAO. Keeps the currently
 * logged-in user so other controllers/views can check the role.
 */
public class LoginController {

    private final UserDAO userDAO = new UserDAO();
    private static User loggedInUser;

    public boolean login(String username, String password) {
        User user = userDAO.validateLogin(username, password);
        if (user != null) {
            loggedInUser = user;
            return true;
        }
        return false;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void logout() {
        loggedInUser = null;
    }
}
