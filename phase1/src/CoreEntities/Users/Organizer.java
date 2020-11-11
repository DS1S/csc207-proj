package CoreEntities.Users;

public class Organizer extends User{

    public Organizer(String username, String password){
        super(username, password);
    }

    private void setAttendeePermissions(){
        permissions.put(Perms.canBeMessaged, false);
        permissions.put(Perms.canCreateEvent, true);
        permissions.put(Perms.canMessageTalk, false);
        permissions.put(Perms.canSchedule, true);
        permissions.put(Perms.canSignUpEvent, false);
        permissions.put(Perms.canSignUpUser, true);
        permissions.put(Perms.canSpeakAtTalk, false);
    }
}

