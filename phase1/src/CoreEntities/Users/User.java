package CoreEntities.Users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.lang.String;


/**
 *  Represents a user entity, contains basic information about a user including:
 *      - UUID
 *      - User's Name
 *      - Username
 *      - Password
 *      - User's Permissions
 */
public abstract class User implements Serializable {
    private final UUID uuid;
    private final String name;
    private final String username;
    private String password;
    protected Map<PERMS, Boolean> permissions;

    /**
     * Creates a User Object with a set name, user name, and password.
     * @param name The name of the user being created.
     * @param username The user name of the user being created.
     * @param password The password of the user being created.
     */
    public User(String name, String username, String password) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.username = username;
        this.password = password;
        permissions = new HashMap<>();
    }

    /**
     * Checks the user's password against another password.
     * @param password The password to validate against the user's password.
     * @return True if the password matches the user's, false otherwise.
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Gets this user's UUID.
     * @return The UUID of the user.
     */
    public UUID getUUID() {
        return this.uuid;
    }

    /**
     * Gets this user's user name.
     * @return The user name of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets this user's full name.
     * @return The full name of the user.
     */
    public String getName() { return this.name; }

    /**
     * Returns the hashmap of permissions of a user; permission keys are based on the
     * PERMS Enum.
     * @return The mapping of permissions for the user.
     */
    public Map<PERMS, Boolean> getPermissions() {
        return permissions;
    }

    /**
     * Sets the permissions of a User by updating the permissions map.
     */
    protected abstract void setPermissions();

}
