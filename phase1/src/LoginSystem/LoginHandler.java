package LoginSystem;

import CoreEntities.Users.User;
import LoginSystem.Exceptions.InvalidPasswordException;
import LoginSystem.Exceptions.InvalidUsernameException;

public class LoginHandler {
    private UserManager um;

    public LoginHandler(UserManager um) {
        this.um = um;
    }

    public void loginUser(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        User u = this.um.getUserWithUsername(username.trim());
        if (u == null) {
            throw new InvalidUsernameException();
        }

        if (u.checkPassword(password.trim())) {
            this.um.setLoggedInUser(u);
        } else {
            throw new InvalidPasswordException();
        }
    }
}
