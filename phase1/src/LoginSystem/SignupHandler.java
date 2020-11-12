package LoginSystem;
import CoreEntities.Users.Attendee;
import CoreEntities.Users.Speaker;
import CoreEntities.Users.User;
import LoginSystem.Exceptions.DuplicateUUIDException;
import LoginSystem.Exceptions.InvalidUsertypeException;
import LoginSystem.Exceptions.UsernameTakenException;

public class SignupHandler {
    private UserManager um;

    public SignupHandler(UserManager um) {
        this.um = um;
    }

    // TODO: remove unexceptional exceptions
    public void signUp(String name, String username, String password, String type) throws UsernameTakenException, DuplicateUUIDException, InvalidUsertypeException {
        if (this.um.containsUserWithUsername(username)) {
            throw new UsernameTakenException();
        }

        this.um.addUser(type, name, username, password);
    }
}
