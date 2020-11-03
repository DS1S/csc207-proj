package LoginSystem;

import CoreEntities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private Map<UUID, User> users;

    public UserManager(List<User> users) throws NullPointerException {
        this.users = new HashMap<>();
        for (User u : users) {
            this.users.put(u.getUUID(), u);
        }
    }

    public void addUser(User u) {
        this.users.put(u.getUUID(), u);
    }

    public void addUsers(List<User> users) {
        for (User u : users) {
            this.users.put(u.getUUID(), u);
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
}
