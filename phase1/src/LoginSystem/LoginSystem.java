package LoginSystem;

import coreUtil.IRunnable;

import java.util.Scanner;

/**
 * A class to handle logging in of users based on input credentials.
 */
public class LoginSystem implements IRunnable {
    private final UserManager um;
    private final AuthenticationUI authUI;

    public LoginSystem(UserManager um){
        this.um = um;
        this.authUI = new AuthenticationUI();
    }

    /**
     *
     * @param username the username to check
     * @param password the password to check
     * @return empty string if successful, error string otherwise, depending on the error
     */
    private String loginUser(String username, String password){
        if (!um.containsUserWithUsername(username.trim())) {
            String INVALID_USERNAME = "Invalid Username!";
            return INVALID_USERNAME;
        }

        if (um.checkPasswordWithUUID(um.getUUIDWithUsername(username), password.trim())) {
            um.setLoggedInUser(um.getUUIDWithUsername(username));
        } else {
            String INVALID_PASSWORD = "Invalid Password!";
            return INVALID_PASSWORD;
        }
        return "";
    }

    /**
     * Displays the UI, prompts, errors, etc. associated with logging in a user.
     */
    public void run(){

        Scanner scanner = new Scanner(System.in);

        String response = "?";
        while (!response.isEmpty()){
            authUI.displayLoginPage();
            authUI.displayUsernamePrompt();
            String username = scanner.nextLine();
            authUI.displayPasswordPrompt();
            String password = scanner.nextLine();
            response = loginUser(username, password);
            System.out.println(response);
            if (response.isEmpty()) authUI.displayError(response);
        }

        scanner.close();
    }
}
