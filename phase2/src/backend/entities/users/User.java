package backend.entities.users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.lang.String;

/**
 *  Represents a user entity, contains basic information about a user including:
 *      - UUID
 *      - User's Name
 *      - Username
 *      - Password
 *      - User's Permissions
 *      - The user's last logged in date
 *      - Whether the User is banned
 *      - The User's profile social media links.
 */
public abstract class User implements Serializable {
    private final UUID uuid;
    private final String name;
    private final String username;
    private final String password;
    protected Map<Perms, Boolean> permissions;
    private LocalDateTime lastLoggedIn;
    private Boolean isBanned;
    private Map<Socials, String> profileLinks;

    /**
     * Creates a User Object with a set name, user name, and password.
     * @param name The name of the user being created.
     * @param username The user name of the user being created.
     * @param password The password of the user being created.
     */
    public User(String name, String username, String password, LocalDateTime registeredTime) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.username = username;
        this.password = password;
        permissions = new HashMap<>();
        this.lastLoggedIn = registeredTime;
        this.isBanned = false;
        this.profileLinks = new HashMap<>();
    }

    /**
     * Sets a User's profile link to link given a social media platform.
     * @param social The social media platform of the link.
     * @param link The link to be set on the profile.
     */
    public void setProfileLink(Socials social, String link) {
        profileLinks.put(social, link);
    }

    /**
     * Removes a User's profile link from a particular social media platform.
     * @param social The social media platform of the link to be removed.
     */
    public void removeProfileLink(Socials social) { profileLinks.remove(social); }

    /**
     * Gets this user's profile links.
     * @return The values of each Socials key in the map of profile links.
     */
    public List<String> getProfileLinks() {
        List<String> links = new ArrayList<>();

        for (Socials social : profileLinks.keySet()) {
            links.add(social.toString() + ": " + profileLinks.get(social));
        }

        return links;
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
     * Returns whether the user is banned.
     * @return True if the user is banned, false otherwise.
     */
    public boolean getIsBanned() { return this.isBanned; }

    /**
     * Sets the ban status of the user.
     * @param banned True if and only if the user is banned.
     */
    public void setBanned(boolean banned){ this.isBanned = banned; }

    /**
     * Returns the hashmap of permissions of a user; permission keys are based on the
     * Perms Enum.
     * @return The mapping of permissions for the user.
     */
    public Map<Perms, Boolean> getPermissions() {
        return permissions;
    }

    /**
     * Sets the permissions of a User by updating the permissions map.
     */
    protected abstract void setPermissions();

    /**
     * Sets the last logged in time user to the date lastLoggedIn.
     * @param lastLoggedIn The new last logged in date.
     */
    public void setLastLoggedIn(LocalDateTime lastLoggedIn) { this.lastLoggedIn = lastLoggedIn; }

    /**
     * Gets the last logged in date for the user.
     */
    public LocalDateTime getLastLoggedIn() { return lastLoggedIn;}

}
