package LoginSystem;

import CoreEntities.User;
import java.util.Scanner;

public class AuthenticationUI {
    private AuthenticationSystem as;

   public AuthenticationUI(AuthenticationSystem as) {
        this.as = as;
    }

    public void displayWelcomePage() {
        System.out.println("        TECH CONFERENCE SYSTEM");
        System.out.println("Logging in? Enter 1. Signing up? Enter 2.");
        System.out.print("Your option: ");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        while (option != 1 && option != 2) {
            System.out.print("Invalid option, please try again: ");
            option = in.nextInt();
        }
        if (option == 1) {
            displayLoginPage();
        } else {
            displaySigningUpPage();
            displayLoginPage();
        }

        System.out.println("Welcome!");
    }

    public void displayLoginPage() {
        System.out.println("Please enter you login details below.");
        Scanner in = new Scanner(System.in);
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();

        while (true) {
            try{
                this.as.LogIn(username, password);
                break;
            } catch (InvalidUsernameException e) {
                System.out.println("Invalid username, please try again.");
                System.out.print("Username: ");
                username = in.nextLine();
            } catch (InvalidPasswordException e) {
                System.out.println("Invalid password, please try again.");
                System.out.print("Password: ");
                password = in.nextLine();
            }
        }
    }

    public void displaySigningUpPage() {
        System.out.println("Please enter you user details below.");
        Scanner in = new Scanner(System.in);
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();

        while (true) {
            try {
                this.as.signUp(username, password);
                break;
            } catch (UsernameTakenException e) {
                System.out.println("This username is taken, try another one.");
                System.out.print("Username: ");
                username = in.nextLine();
            } catch (DuplicateUUIDException e) {
                System.out.println("You are very lucky :) Try again.");
                System.out.print("Username: ");
                username = in.nextLine();
                System.out.print("Password: ");
                password = in.nextLine();
            }
        }
    }
}
