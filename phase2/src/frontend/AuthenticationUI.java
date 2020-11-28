package frontend;

import java.util.List;
import java.util.Scanner;

/**
 * UI class for printing various prompts and errors to the screen as needed.
 */
public class AuthenticationUI {
    /**
     * Displays the welcome page upon starting the program.
     */
    public void displayWelcomePage() {
        System.out.println("        TECH CONFERENCE SYSTEM");
        System.out.println("Logging in? Enter 1. Signing up? Enter 2.");
        System.out.print("Your option: ");
    }

    /**
     * Displays an invalid option error.
     */
    public void displayOptionError() {
       System.out.println("Invalid option, please try again: ");
    }

    /**
     * Asks the user for his full name.
     */
    public void displayNamePrompt() { System.out.print("Full Name: "); }

    /**
     * Asks the user for his username.
     */
    public void displayUsernamePrompt() {
        System.out.print("Username: ");
    }

    /**
     * Asks the user for his password.
     */
    public void displayPasswordPrompt() {
        System.out.print("Password: ");
    }

    /**
     * Displays a error of the caller's choosing.
     * @param e The error to discuss.
     */
    public void displayError(String e) { System.out.println(e); }

    /**
     * Displays the login page to the user.
     */
    public void displayLoginPage() {
        System.out.println("Enter your credentials below.");
    }

    /**
     * Displays the sign up page for new users.
     */
    public void displaySigningUpPage() {
        System.out.println("Please enter your user details below.");
    }

    /**
     * Displays a list of user types the user is allowed to create.
     * @param types The list of types to show. Caller's responsibility to display valid types.
     */
    public void displayUserTypes(List<String> types) {
        System.out.println("Choose a user type to create: ");
        for (int i = 0; i < types.size(); i++) {
            System.out.println((i + 1) + ": " + types.get(i));
        }
    }
}
