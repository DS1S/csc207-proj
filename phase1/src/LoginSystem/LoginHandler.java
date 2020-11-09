package LoginSystem;

import CoreEntities.User;

public class LoginHandler {

    public LoginHandler() {}

    public User loginUser(String username, String password, UserManager userManager) {
        User u = userManager.getUserWithUsername(username.trim());
        if (u == null) {
            // Should probably contact presenter here or something.
            System.out.println("Invalid username");
            return null;
        }

        if (u.checkPassword(password.trim())) {
            return u;
        } else {
            // Should probably contact presenter here or something.
            System.out.println("Invalid password");
            return null;
        }
    }
}
