package CoreEntities.Users;

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
    public Organizer(String name, String username, String password) {
        super(name, username, password);
        setPermissions();
    }

    /**
     * Sets the permissions of an Organizer. Organizers can schedule events and create users.
     */
    protected void setPermissions() {
        permissions.put(PERMS.canBeMessaged, false);
        permissions.put(PERMS.canMessageTalk, false);
        permissions.put(PERMS.canSchedule, true);
        permissions.put(PERMS.canSignUpEvent, false);
        permissions.put(PERMS.canSignUpUser, true);
        permissions.put(PERMS.canSpeakAtTalk, false);
    }
}

