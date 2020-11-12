package LoginSystem;

import CoreEntities.Users.User;
import LoginSystem.Exceptions.InvalidPasswordException;
import LoginSystem.Exceptions.InvalidUsernameException;

public class LoginHandler {
    private UserManager um;

    public LoginHandler(UserManager um) {
        this.um = um;
    }

    // TODO: remove unexceptional exceptions
    public void loginUser(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        User u = this.um.containsUserWithUsername(username.trim());
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
