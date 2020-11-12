package LoginSystem;

import CoreEntities.Users.Attendee;
import CoreEntities.Users.Speaker;
import CoreEntities.Users.User;

public class UserFactory {

    public User buildUser(String userType, String name, String username, String password){
        if (userType.equals("speaker")) {
            return new Speaker(name, username, password);
        } else if (userType.equals("attendee")) {
            return new Attendee(name, username, password);
        }
        return null;
    }
}
