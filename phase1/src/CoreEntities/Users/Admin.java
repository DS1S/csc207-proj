package CoreEntities.Users;

public class Admin extends User{

    public Admin(String name, String username, String password){
        super(name, username, password);
        setPermissions();
    }

    @Override
    protected void setPermissions() {
        permissions.put(PERMS.canBeMessaged, false);
        permissions.put(PERMS.canMessageTalk, false);
        permissions.put(PERMS.canSchedule, true);
        permissions.put(PERMS.canSignUpEvent, false);
        permissions.put(PERMS.canSignUpUser, true);
        permissions.put(PERMS.canSpeakAtTalk, false);
    }
}
