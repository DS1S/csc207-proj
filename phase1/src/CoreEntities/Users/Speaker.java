package CoreEntities.Users;

public class Speaker extends User {

    public Speaker(String username, String password){
        super(username, password);
    }

    protected void setPermissions(){
        permissions.put(Perms.canBeMessaged, true);
        permissions.put(Perms.canCreateEvent, false);
        permissions.put(Perms.canMessageTalk, true);
        permissions.put(Perms.canSchedule, false);
        permissions.put(Perms.canSignUpEvent, false);
        permissions.put(Perms.canSignUpUser, false);
        permissions.put(Perms.canSpeakAtTalk, true);
    }
}
