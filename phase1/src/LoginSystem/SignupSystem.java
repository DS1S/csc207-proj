package LoginSystem;

import coreUtil.IRunnable;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * A class which handles the creation of new Users of allowed types.
 */
public class SignupSystem implements IRunnable {
    private final UserManager um;
    private final AuthenticationUI authUI;
    private final List<String> possibleTypes;
    private final Scanner scanner = new Scanner(System.in);

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

    private String signUp(String name, String username, String password, String type){
        if (this.um.containsUserWithUsername(username)) {
            String TAKEN_USERNAME = "That username is taken!";
            return TAKEN_USERNAME;
        }

        this.um.addUser(type, name, username, password);
        return "";
    }


    private void create(String type){

        String result = "?";
        while(!result.isEmpty()){
            this.authUI.displaySigningUpPage();

            this.authUI.displayNamePrompt();
            String name = askForString("Name");

            this.authUI.displayUsernamePrompt();
            String username = askForString("Username");

            this.authUI.displayPasswordPrompt();
            String password = askForString("Password");

            result = signUp(name, username, password, type);
            if (!result.isEmpty()) authUI.displayError(result);
        }
    }

    private String askForString(String attribute){
        String string = "";
        while (string.isEmpty()){
            string = scanner.nextLine();
            if (string.isEmpty()) authUI.displayError(attribute + " is empty, please input a " + attribute + "!");
        }
        return string;
    }

    /**
     * Prompts user to select type of user to create based on permissions, then calls create() to handle the rest.
     * Also runs recursively until valid input.
     */
    public void run(){

        int option = 0;
        while(option <= 0 || option > possibleTypes.size()){
            authUI.displayUserTypes(possibleTypes);
            try{
                option = scanner.nextInt();
                if (option <= 0 || option > possibleTypes.size()) authUI.displayError("Invalid option, try again!");
            }
            catch (InputMismatchException e){
                authUI.displayError("Invalid option, try again!");
            }
        }
        create(possibleTypes.get(option - 1));
    }
}
