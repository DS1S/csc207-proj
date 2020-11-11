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

    public void signUp(String username, String password, String type) throws UsernameTakenException, DuplicateUUIDException, InvalidUsertypeException {
        if (this.um.getUserWithUsername(username) != null) {
            throw new UsernameTakenException();
        }

        User u;
        if (type == "speaker") {
            u = new Speaker(username, password);
        } else if (type == "attendee") {
            u = new Attendee(username, password);
        } else {
            throw new InvalidUsertypeException();
        }

        if (this.um.getUserWithUUID(u.getUUID()) != null) {
            throw new DuplicateUUIDException();
        }
        this.um.addUser(u);
    }
}
