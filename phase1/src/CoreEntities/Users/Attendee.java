package CoreEntities.Users;

public class Attendee extends User{

    public Attendee(String name, String username, String password){
        super(name, username, password);
        setAttendeePermissions();
    }

    private void setAttendeePermissions(){
        permissions.put(Perms.canBeMessaged, true);
        permissions.put(Perms.canCreateEvent, false);
        permissions.put(Perms.canMessageTalk, false);
        permissions.put(Perms.canSchedule, false);
        permissions.put(Perms.canSignUpEvent, true);
        permissions.put(Perms.canSignUpUser, false);
        permissions.put(Perms.canSpeakAtTalk, false);
    }
}
