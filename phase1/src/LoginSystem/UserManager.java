package LoginSystem;

import CoreEntities.Users.Perms;
import CoreEntities.Users.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;

public class UserManager implements Serializable {
    private Map<UUID, User> users;
    private User loggedInUser;

    public UserManager(){
        this.users = new HashMap<UUID, User>();
    }

    public void addUser(String type, String username, String password, String name) {
        UserFactory userCreator = new UserFactory();
        User u;

        do {
            u = userCreator.buildUser(type, name, username, password);
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

    public boolean loggedInHasPermission(Perms permission){
        return this.loggedInUser.getPermissions().get(permission);
    }

    public String getUsernameWithUUID(UUID userUUID){
        return users.get(userUUID).getUsername();
    }

    public boolean hasPermission(UUID userID, Perms permission){
        return this.users.get(userID).getPermissions().get(permission);
    }

    public List<UUID> getUUIDs(){
        return new ArrayList<>(this.users.keySet());
    }

}
