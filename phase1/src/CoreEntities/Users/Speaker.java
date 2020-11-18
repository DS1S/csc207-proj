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
    public Speaker(String name, String username, String password){
        super(name, username, password);
        setPermissions();
    }

    protected void setPermissions(){
        permissions.put(Perms.canBeMessaged, true);
        permissions.put(Perms.canMessageTalk, true);
        permissions.put(Perms.canSchedule, false);
        permissions.put(Perms.canSignUpEvent, false);
        permissions.put(Perms.canSignUpUser, false);
        permissions.put(Perms.canSpeakAtTalk, true);
    }
}
