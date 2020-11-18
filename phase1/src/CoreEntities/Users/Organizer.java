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

    protected void setPermissions() {
        permissions.put(Perms.canBeMessaged, false);
        permissions.put(Perms.canMessageTalk, false);
        permissions.put(Perms.canSchedule, true);
        permissions.put(Perms.canSignUpEvent, false);
        permissions.put(Perms.canSignUpUser, true);
        permissions.put(Perms.canSpeakAtTalk, false);
    }
}

