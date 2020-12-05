package backend.entities.users;

import java.time.LocalDateTime;

/**
 * Represents an organizer type user.
 */
public class Organizer extends User {
    /**
     * Creates an Organizer Object and set its permissions accordingly based on its type.
     * @param name Name of the organizer.
     * @param username Username of the organizer.
     * @param password Password of the organizer.
     */
    public Organizer(String name, String username, String password, LocalDateTime lastLoggedIn) {
        super(name, username, password, lastLoggedIn);
        setPermissions();
    }

    /**
     * Sets the permissions of an Organizer. Organizers can schedule events and create users.
     */
    protected void setPermissions() {
        permissions.put(Perms.CAN_BE_MESSAGED, false);
        permissions.put(Perms.CAN_MESSAGE_TALK, false);
        permissions.put(Perms.CAN_SCHEDULE, true);
        permissions.put(Perms.CAN_SIGN_UP_EVENT, false);
        permissions.put(Perms.CAN_SIGN_UP_USER, true);
        permissions.put(Perms.CAN_SPEAK_AT_TALK, false);
        permissions.put(Perms.CAN_VIEW_STATS, true);
        permissions.put(Perms.CAN_BAN_USERS, false);
        permissions.put(Perms.CAN_SEE_ALL_MESSAGES, false);
    }
}

