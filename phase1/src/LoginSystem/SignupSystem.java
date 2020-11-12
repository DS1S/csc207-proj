package LoginSystem;

import coreUtil.IRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SignupSystem implements IRunnable {
    private final String TAKEN_USERNAME = "That username is taken!";
    private UserManager um;
    private AuthenticationUI authUI;
    private List<String> possibleTypes;

    public SignupSystem(UserManager um, String type){
        this.um = um;
        this.authUI = new AuthenticationUI();
        this.possibleTypes = new ArrayList<String>();
        this.possibleTypes.add(type);
    }

    public SignupSystem(UserManager um, List<String> possibleTypes){
        this.um = um;
        this.authUI = new AuthenticationUI();
        this.possibleTypes = possibleTypes;
    }

    //returns error string, "" on success.
    public String signUp(String name, String username, String password, String type){
        if (this.um.containsUserWithUsername(username)) {
            return TAKEN_USERNAME;
        }

        this.um.addUser(type, name, username, password);
        return "";
    }

    private void create(String type){
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

    public void run(){
        // TODO: Clear window?
        // TODO: prompt user to select type, cooresponding to possibleTypes index.
        int input = 0; // TODO: Take input
        if (input >= possibleTypes.size()){
            //invalid type (out of bounds)
            // TODO: display error
            run();
        }
        create(possibleTypes.get(0));

        if (possibleTypes.size() == 1){
            create(possibleTypes.get(0));
        }
    }
}
