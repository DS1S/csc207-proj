package backend.systems.usermangement;

import backend.systems.usermangement.managers.UserManager;
import frontend.AuthenticationUI;
import utility.RunnableSystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * A class which handles the creation of new Users of allowed types.
 */
public class SignupSystem implements RunnableSystem {
    private final UserManager um;
    private final AuthenticationUI authUI;
    private final List<String> possibleTypes;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a SignupSystem using the given UserManager and for the type.
     * @param um The UserManager to work with.
     * @param type The type this Signup system is allowed to use.
     */
    public SignupSystem(UserManager um, String type) {
        this.um = um;
        this.authUI = new AuthenticationUI();
        this.possibleTypes = new ArrayList<>();
        this.possibleTypes.add(type);
    }

    /**
     * Same as the previous constructor, but allows a list of types.
     * @param um The UserManager to work with.
     * @param possibleTypes The list of allowed types.
     */
    public SignupSystem(UserManager um, List<String> possibleTypes) {
        this.um = um;
        this.authUI = new AuthenticationUI();
        this.possibleTypes = possibleTypes;
    }

    private String signUp(String name, String username, String password, String type) {
        if (this.um.containsUserWithUsername(username)) {
            return "That username is taken!";
        }

        this.um.addUser(type, username, password, name);
        return "";
    }

    private void create(String type) {
        String result = "?";

        while(!result.isEmpty()) {
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

    private String askForString(String attribute) {
        String string = "";

        while (string.isEmpty()) {
            string = scanner.nextLine();
            if (string.isEmpty()) authUI.displayError(attribute + " is empty, please input a " + attribute + "!");
        }
        return string;
    }

    /**
     * Prompts user to select type of user to create based on permissions, then calls create() to handle the rest.
     * Also runs recursively until valid input.
     */
    public void run() {
        int option = 0;

        while(option <= 0 || option > possibleTypes.size()) {
            authUI.displayUserTypes(possibleTypes);
            try{
                option = scanner.nextInt();
                if (option <= 0 || option > possibleTypes.size()) authUI.displayError("Invalid option, try again!");
                scanner.nextLine();
            }
            catch (InputMismatchException e) {
                authUI.displayError("Invalid option, try again!");
                scanner.nextLine();
            }
        }
        create(possibleTypes.get(option - 1));
    }

    /**
     * A string representation of this class.
     * @return the string representation.
     */
    @Override
    public String toString() {
        return "User Creation";
    }
}
