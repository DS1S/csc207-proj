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
     * @param userType A string representing the type of this user.
     * @param name The user's full name.
     * @param username The user's user name. Is presumed to be unique and checked by the caller.
     * @param password The user's password.
     * @return A User object if the userType is valid, null otherwise.
     */
    public User buildUser(String userType, String name, String username, String password) {
        switch (userType) {
            case "speaker":
                return new Speaker(name, username, password);
            case "attendee":
                return new Attendee(name, username, password);
            case "organizer":
                return new Organizer(name, username, password);
        }
        return null;
    }
}
