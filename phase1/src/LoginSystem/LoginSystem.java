package LoginSystem;

import Presenters.AuthenticationUI;
import coreUtil.IRunnable;

import java.util.Scanner;

/**
 * A class to handle logging in of users based on input credentials.
 */
public class LoginSystem implements IRunnable {
    private final UserManager um;
    private final AuthenticationUI authUI;

    /**
     * Constructs a LoginSystem to use the given UserManager.
     * @param um The UserManager to work with.
     */
    public LoginSystem(UserManager um) {
        this.um = um;
        this.authUI = new AuthenticationUI();
    }

    private String loginUser(String username, String password) {
        if (!um.containsUserWithUsername(username.trim())) {
            return "Invalid Username!";
        }

        if (um.checkPasswordWithUUID(um.getUUIDWithUsername(username), password.trim())) {
            if(um.checkBannedWithUUID(um.getUUIDWithUsername(username))){
                return "Banned User!";
            }

            um.setLoggedInUser(um.getUUIDWithUsername(username));
        } else {
            return "Invalid Password!";
        }

        return "";
    }

    /**
     * Displays the UI, prompts, errors, etc. associated with logging in a user.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        String response = "?";
        while (!response.isEmpty()) {
            authUI.displayLoginPage();
            authUI.displayUsernamePrompt();
            String username = scanner.nextLine();

            authUI.displayPasswordPrompt();
            String password = scanner.nextLine();

            response = loginUser(username, password);
            System.out.println(response);
            if (response.isEmpty()) authUI.displayError(response);
        }
    }
}
