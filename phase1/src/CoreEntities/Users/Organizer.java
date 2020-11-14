package CoreEntities.Users;

public class Organizer extends User {

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

