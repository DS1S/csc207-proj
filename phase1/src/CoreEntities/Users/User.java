package CoreEntities.Users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.lang.String;



public abstract class User implements Serializable {

    private UUID uuid;
    private String name;
    private String username;
    private String password;
    protected Map<Perms, Boolean> permissions;

    public User(String name, String username, String password) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.username = username;
        this.password = password;
        permissions = new HashMap<>();

    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public Map<Perms, Boolean> getPermissions(){
        return permissions;
    }

    protected abstract void setPermissions();

}
