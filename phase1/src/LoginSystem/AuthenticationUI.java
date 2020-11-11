package LoginSystem;

import java.util.Scanner;

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

    public void displayError(String e) {
        System.out.print(e);
    }

    public void displayLoginPage() {
        System.out.print("Enter your credentials below.");
    }

    public void displaySigningUpPage() {
        System.out.println("Please enter you user details below.");
    }
}
