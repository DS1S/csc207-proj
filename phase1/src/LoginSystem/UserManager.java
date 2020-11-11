package LoginSystem;

import CoreEntities.Users.Perms;
import CoreEntities.Users.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private Map<UUID, User> users;
    private User loggedInUser;

    public UserManager(){
        this.users = new HashMap<UUID, User>();
    }

    public UserManager(List<User> users) throws NullPointerException {
        this.users = new HashMap<UUID, User>();
        for (User u : users) {
            this.users.put(u.getUUID(), u);
        }
        this.loggedInUser = null;
    }

    public void addUser(User u) {
        this.users.put(u.getUUID(), u);
    }

    public void addUsers(List<User> users) {
        for (User u : users) {
            addUser(u);
        }
    }

    public User getUserWithUUID(UUID id) {
        return this.users.get(id);
    }

    public User getUserWithUsername(String username) {
        for (User u : this.users.values()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
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

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean loggedInHasPermission(Perms permission){
        Boolean hasPerm = this.loggedInUser.getPermissions().get(permission);
        return hasPerm;
    }
}
