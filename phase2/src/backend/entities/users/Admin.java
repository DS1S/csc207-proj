package backend.entities.users;

import java.time.LocalDateTime;

/**
 * Represents an Admin type User.
 */
public class Admin extends User{

    /**
     * Creates an Admin Object and set its permissions accordingly based on its type.
     * @param name Name of the Admin.
     * @param username Username of the Admin.
     * @param password Password of the Admin.
     */
    public Admin(String name, String username, String password, LocalDateTime lastLoggedIn){
        super(name, username, password, lastLoggedIn);
        setPermissions();
    }

    /**
     * Sets the permissions of an Admin. Admins can schedule events, create users, ban users, manage messages,
     * and view statistics
     */
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
