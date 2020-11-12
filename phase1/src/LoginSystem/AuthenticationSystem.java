package LoginSystem;

import CoreEntities.Users.User;
import coreUtil.IRunnable;

import java.util.Scanner;

public class AuthenticationSystem implements IRunnable {
    private AuthenticationUI authUI;
    private UserManager um;
    private final String INVALID_USERNAME = "Invalid Username!";
    private final String INVALID_PASSWORD = "Invalid Password!";
    private final String TAKEN_USERNAME = "That username is taken!";
    private final String ATTENDEE_TYPE = "attendee";

    public AuthenticationSystem(UserManager userManager){
        this.um = userManager;
        authUI = new AuthenticationUI();
    }

    //returns error string, "" on success.
    private String loginUser(String username, String password){
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

    //returns error string, "" on success.
    public String signUp(String name, String username, String password, String type){
        if (this.um.containsUserWithUsername(username)) {
            return TAKEN_USERNAME;
        }

        this.um.addUser(type, name, username, password);
        return "";
    }

    private void runSignUp(Scanner scanner){
        // TODO: Clear window?
        this.authUI.displayLoginPage();
        this.authUI.displayUsernamePrompt();
        String username = scanner.nextLine();
        this.authUI.displayPasswordPrompt();
        String password = scanner.nextLine();

        if (loginUser(username, password) != "") {
            authUI.displayError(loginUser(username, password));
            runSignUp(scanner);
        }
    }

    private void runLogin(Scanner scanner){
        // TODO: Clear window?
        this.authUI.displaySigningUpPage();
        this.authUI.displayNamePrompt();
        String name = scanner.nextLine();
        this.authUI.displayUsernamePrompt();
        String username = scanner.nextLine();
        this.authUI.displayPasswordPrompt();
        String password = scanner.nextLine();

        String result = signUp(name, username, password, ATTENDEE_TYPE);
        if (result != ""){
            authUI.displayError(result);
            runLogin(scanner);
        }
    }

    @Override
    public void run() {
        this.authUI.displayWelcomePage();
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        while (option != 1 && option != 2) {
            this.authUI.displayOptionError();
            option = scanner.nextInt();
        }

        switch(option) {
            case(1):
                runSignUp(scanner);
                break;

            case(2):
                runLogin(scanner);
                break;
        }


    }
}
