package backend.entities.users;


import java.time.LocalDateTime;

/**
 *  Represents a speaker type user.
 */
public class Speaker extends User {
    /**
     * Creates a Speaker object and set its permissions accordingly based on its type.
     * @param name Name of the speaker.
     * @param username Username of the speaker.
     * @param password Password of the speaker.
     */
    public Speaker(String name, String username, String password, LocalDateTime lastLoggedIn) {
        super(name, username, password, lastLoggedIn);
        setPermissions();
    }

    /**
     * Sets the permissions of a speaker. Speakers can be messaged, message everyone in their
     * talk(s), and can speak at a talk.
     */
    protected void setPermissions() {
        permissions.put(Perms.CAN_BE_MESSAGED, true);
        permissions.put(Perms.CAN_MESSAGE_TALK, true);
        permissions.put(Perms.CAN_SCHEDULE, false);
        permissions.put(Perms.CAN_SIGN_UP_EVENT, false);
        permissions.put(Perms.CAN_SIGN_UP_USER, false);
        permissions.put(Perms.CAN_SPEAK_AT_TALK, true);
        permissions.put(Perms.CAN_VIEW_STATS, false);
        permissions.put(Perms.CAN_BAN_USERS, false);
        permissions.put(Perms.CAN_SEE_ALL_MESSAGES, false);
    }
}
