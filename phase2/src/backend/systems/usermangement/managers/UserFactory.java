package backend.systems.usermangement.managers;

import backend.entities.users.Attendee;
import backend.entities.users.Organizer;
import backend.entities.users.Speaker;
import backend.entities.users.User;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
                return new Speaker(name, username, password, LocalDateTime.now());
            case "attendee":
                return new Attendee(name, username, password, LocalDateTime.now());
            case "organizer":
                return new Organizer(name, username, password, LocalDateTime.now());
        }
        return null;
    }
}
