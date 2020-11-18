package CoreEntities.Users;

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

    protected void setPermissions() {
        permissions.put(Perms.canBeMessaged, true);
        permissions.put(Perms.canMessageTalk, false);
        permissions.put(Perms.canSchedule, false);
        permissions.put(Perms.canSignUpEvent, true);
        permissions.put(Perms.canSignUpUser, false);
        permissions.put(Perms.canSpeakAtTalk, false);
    }
}
