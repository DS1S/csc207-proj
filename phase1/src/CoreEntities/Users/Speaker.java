package CoreEntities.Users;

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
    public Speaker(String name, String username, String password) {
        super(name, username, password);
        setPermissions();
    }

    /**
     * Sets the permissions of a speaker. Speakers can be messaged, message everyone in their
     * talk(s), and can speak at a talk.
     */
    protected void setPermissions() {
        permissions.put(PERMS.canBeMessaged, true);
        permissions.put(PERMS.canMessageTalk, true);
        permissions.put(PERMS.canSchedule, false);
        permissions.put(PERMS.canSignUpEvent, false);
        permissions.put(PERMS.canSignUpUser, false);
        permissions.put(PERMS.canSpeakAtTalk, true);
    }
}
