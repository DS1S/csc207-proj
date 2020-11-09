package CoreEntities;

import java.io.Serializable;
import java.util.UUID;
import java.lang.String;



public class User implements Serializable {
    public enum AccountType {
        Attendee,
        Organizer,
        Speaker
    }

    private UUID uuid;
    private String username;
    private String password;
    private AccountType type;

    public User(String username, String password, AccountType type) {
        this.uuid = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.type = type;
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
}
