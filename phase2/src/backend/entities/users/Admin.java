package backend.entities.users;

import java.time.LocalDateTime;

public class Admin extends User{

    public Admin(String name, String username, String password, LocalDateTime lastLoggedIn){
        super(name, username, password, lastLoggedIn);
        setPermissions();
    }

    @Override
    protected void setPermissions() {
        permissions.put(Perms.CAN_BE_MESSAGED, false);
        permissions.put(Perms.CAN_MESSAGE_TALK, false);
        permissions.put(Perms.CAN_SCHEDULE, true);
        permissions.put(Perms.CAN_SIGN_UP_EVENT, false);
        permissions.put(Perms.CAN_SIGN_UP_USER, true);
        permissions.put(Perms.CAN_SPEAK_AT_TALK, false);
        permissions.put(Perms.CAN_VIEW_STATS, true);
        permissions.put(Perms.CAN_BAN_USERS, true);
        permissions.put(Perms.CAN_SEE_ALL_MESSAGES, true);
    }
}
