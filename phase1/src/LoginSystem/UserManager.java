package LoginSystem;

import CoreEntities.Users.Perms;
import CoreEntities.Users.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class the manages a list of User objects.
 */
public class UserManager {
    private Map<UUID, User> users;
    private User loggedInUser;


    public UserManager(){
        this.users = new HashMap<UUID, User>();
    }

    /**
     * Adds a user to the list of users managed by UserManager.
     *
     * @param type A string which represents the type of this user, used by the UserFactory to make the appropriate user. Is presumed to be valid, checked by caller.
     * @param username username uniqueness is presumed checked by the caller.
     * @param password the user's password
     * @param name the user's real-life name
     */
    public void addUser(String type, String username, String password, String name) {
        UserFactory userCreator = new UserFactory();
        User u;

        do {
            u = userCreator.buildUser(type, username, password, name);
        }while(containsUserWithUUID(u.getUUID()));

        this.users.put(u.getUUID(), u);
    }

    public boolean containsUserWithUUID(UUID id) {
        return users.containsKey(id);
    }

    public boolean containsUserWithUsername(String username) {
        for (User u : this.users.values()) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if password matches that of the the user with UUID id.
     *
     * @param id is presumed to be unique.
     * @param password the input password to check against stored.
     * @return true if password matches stored password, false otherwise
     */
    public boolean checkPasswordWithUUID(UUID id, String password){
        return users.get(id).checkPassword(password);
    }

    public UUID getUUIDWithUsername(String username) {
        for (User u : this.users.values()) {
            if (u.getUsername().equals(username)) {
                return u.getUUID();
            }
        }
        return null;
    }

    public UUID getLoggedInUserUUID() {
        return loggedInUser.getUUID();
    }

    public void setLoggedInUser(UUID userID) {
        this.loggedInUser = this.users.get(userID);
    }

    /**
     * Checks if the logged in user has a particular permission.
     *
     * @param permission a permission from the Perms enum
     * @return true if the logged-in user has this permission, false otherwise
     */
    public boolean loggedInHasPermission(Perms permission){
        return this.loggedInUser.getPermissions().get(permission);
    }
}
