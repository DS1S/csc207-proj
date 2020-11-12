package LoginSystem;

import coreUtil.IRunnable;

import java.util.Scanner;

public class SignupSystem implements IRunnable {
    private final String TAKEN_USERNAME = "That username is taken!";
    private UserManager um;
    private String type;
    private AuthenticationUI authUI;

    public SignupSystem(UserManager um, String type){
        this.um = um;
        this.type = type;
        this.authUI = new AuthenticationUI();
    }

    //returns error string, "" on success.
    public String signUp(String name, String username, String password, String type){
        if (this.um.containsUserWithUsername(username)) {
            return TAKEN_USERNAME;
        }

        this.um.addUser(type, name, username, password);
        return "";
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        // TODO: Clear window?
        this.authUI.displaySigningUpPage();
        this.authUI.displayNamePrompt();
        String name = scanner.nextLine();
        this.authUI.displayUsernamePrompt();
        String username = scanner.nextLine();
        this.authUI.displayPasswordPrompt();
        String password = scanner.nextLine();

        String result = signUp(name, username, password, type);
        if (result != ""){
            authUI.displayError(result);
            run();
        }
    }
}
