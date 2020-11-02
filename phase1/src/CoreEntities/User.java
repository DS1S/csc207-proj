package CoreEntities;

import java.io.Serializable;
import java.util.UUID;
import java.lang.String;

enum AccountType {
    Attendee,
    Organizer,
    Speaker
}

public class User implements Serializable {
    private UUID uuid;

    private String username;
    private String password;

    private String name;
    private String email;

    private AccountType type;

    boolean validateCredentials(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
