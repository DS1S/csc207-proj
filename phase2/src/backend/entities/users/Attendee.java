package backend.entities.users;

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
    public Attendee(String name, String username, String password) {
        super(name, username, password);
        setPermissions();
    }

    /**
     * Sets the permissions of an Attendee. Attendees can be messaged and are able to
     * sign up for events.
     */
    protected void setPermissions() {
        permissions.put(PERMS.canBeMessaged, true);
        permissions.put(PERMS.canMessageTalk, false);
        permissions.put(PERMS.canSchedule, false);
        permissions.put(PERMS.canSignUpEvent, true);
        permissions.put(PERMS.canSignUpUser, false);
        permissions.put(PERMS.canSpeakAtTalk, false);
    }
}
