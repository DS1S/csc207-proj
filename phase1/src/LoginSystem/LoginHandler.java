package LoginSystem;

import CoreEntities.User;

public class LoginHandler {
    UserManager umgr;

    public LoginHandler(UserManager umgr) {
        this.umgr = umgr;
    }

    public User loginUser(String username, String password) {
        User u = this.umgr.getUserWithUsername(username.trim());
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
