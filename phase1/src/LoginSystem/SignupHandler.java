package LoginSystem;
import CoreEntities.Users.Attendee;
import CoreEntities.Users.User;

class UsernameTakenException extends Exception {}
class DuplicateUUIDException extends Exception {}

public class SignupHandler {
    private UserManager um;

    public SignupHandler(UserManager um) {
        this.um = um;
    }

    public void signUp(String username, String password) throws UsernameTakenException, DuplicateUUIDException {
        if (this.um.getUserWithUsername(username) != null) {
            throw new UsernameTakenException();
        }

        User u = new Attendee(username, password);
        if (this.um.getUserWithUUID(u.getUUID()) != null) {
            throw new DuplicateUUIDException();
        }
        this.um.addUser(u);
    }

}
