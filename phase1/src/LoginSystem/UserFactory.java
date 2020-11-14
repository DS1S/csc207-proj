package LoginSystem;

import CoreEntities.Users.Attendee;
import CoreEntities.Users.Organizer;
import CoreEntities.Users.Speaker;
import CoreEntities.Users.User;

/**
 * A Factory class to manufacture a User based on their type.
 */
public class UserFactory {
    /**
     * Makes a user of a specific type based on the passed in userType string and other details.
     *
     * @param userType a string representing the type of this user.
     * @param name the user's real-life name
     * @param username is presumed to be unique and checked by the caller.
     * @param password The user's password
     * @return a User object if the userType is valid, null otherwise.
     */
    public User buildUser(String userType, String name, String username, String password) {
        if (userType.equals("speaker")) {
            return new Speaker(name, username, password);
        } else if (userType.equals("attendee")) {
            return new Attendee(name, username, password);
        }
        else if (userType.equals("organizer")) {
            return new Organizer(name, username, password);
        }
        return null;
    }
}
