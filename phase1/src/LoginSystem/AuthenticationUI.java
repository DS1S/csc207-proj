package LoginSystem;

import java.util.List;
import java.util.Scanner;

/**
 * UI class for printing various prompts and errors to the screen as needed.
 */
public class AuthenticationUI {
    public void displayWelcomePage() {
        System.out.println("        TECH CONFERENCE SYSTEM");
        System.out.println("Logging in? Enter 1. Signing up? Enter 2.");
        System.out.print("Your option: ");
    }

    public void displayOptionError() {
       System.out.println("Invalid option, please try again: ");
    }

    public void displayNamePrompt() { System.out.print("Full Name: "); }

    public void displayUsernamePrompt() {
        System.out.print("Username: ");
    }

    public void displayPasswordPrompt() {
        System.out.print("Password: ");
    }

    public void displayError(String e) { System.out.println(e); }

    public void displayLoginPage() {
        System.out.println("Enter your credentials below.");
    }

    public void displaySigningUpPage() {
        System.out.println("Please enter your user details below.");
    }

    public void displayUserTypes(List<String> types){
        System.out.println("Choose a user type to create: ");
        for (int i = 0; i < types.size(); i++){
            System.out.println((i + 1) + ": " + types.get(i));
        }
    }
}
