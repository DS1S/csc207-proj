package LoginSystem;

import CoreEntities.User;

public class LoginHandler {
    private UserManager um;

    public LoginHandler(UserManager um) {
        this.um = um;
    }

    public User loginUser(String username, String password) {
        User u = this.um.getUserWithUsername(username.trim());
        if (u == null) {
            return null;
        }

        if (u.checkPassword(password.trim())) {
            return u;
        } else {
            return null;
        }
    }
}
