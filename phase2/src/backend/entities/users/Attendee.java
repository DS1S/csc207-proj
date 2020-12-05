package backend.entities.users;

import java.time.LocalDateTime;

/**
 * Represents an Attendee type User.
 */
public class Attendee extends User {
    /**
     * Creates an Attendee Object and set its permissions accordingly based on its type.
     * @param name Name of the organizer.
     * @param username Username of the organizer.
     * @param password Password of the organizer.
     */
    public Attendee(String name, String username, String password, LocalDateTime lastLoggedIn) {
        super(name, username, password, lastLoggedIn);
        setPermissions();
    }

    /**
     * Sets the permissions of an Attendee. Attendees can be messaged and are able to
     * sign up for events.
     */
    protected void setPermissions() {
        permissions.put(Perms.CAN_BE_MESSAGED, true);
        permissions.put(Perms.CAN_MESSAGE_TALK, false);
        permissions.put(Perms.CAN_SCHEDULE, false);
        permissions.put(Perms.CAN_SIGN_UP_EVENT, true);
        permissions.put(Perms.CAN_SIGN_UP_USER, false);
        permissions.put(Perms.CAN_SPEAK_AT_TALK, false);
        permissions.put(Perms.CAN_VIEW_STATS, false);
        permissions.put(Perms.CAN_BAN_USERS, false);
        permissions.put(Perms.CAN_SEE_ALL_MESSAGES, false);
    }
}
