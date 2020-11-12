package LoginSystem;

import coreUtil.IRunnable;

import java.util.Scanner;


public class LoginSystem implements IRunnable {
    private final String INVALID_USERNAME = "Invalid Username!";
    private final String INVALID_PASSWORD = "Invalid Password!";
    private UserManager um;
    AuthenticationUI authUI;

    public LoginSystem(UserManager um){
        this.um = um;
        this.authUI = new AuthenticationUI();
    }

    //returns error string, "" on success.
    private String loginUser(String username, String password, UserManager um){
        if (!um.containsUserWithUsername(username.trim())) {
            return INVALID_USERNAME;
        }

        if (um.checkPasswordWithUUID(um.getUUIDWithUsername(username), password.trim())) {
            um.setLoggedInUser(um.getUUIDWithUsername(username));
        } else {
            return INVALID_PASSWORD;
        }
        return "";
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);

        // TODO: Clear window?
        authUI.displayLoginPage();
        authUI.displayUsernamePrompt();
        String username = scanner.nextLine();
        authUI.displayPasswordPrompt();
        String password = scanner.nextLine();

        if (loginUser(username, password, um) != "") {
            authUI.displayError(loginUser(username, password, um));
            run();
        }
        scanner.close();
    }
}
