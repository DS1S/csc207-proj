package CoreEntities.Users;

public class Speaker extends User {

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
