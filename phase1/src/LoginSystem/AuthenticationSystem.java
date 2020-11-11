package LoginSystem;

import LoginSystem.Exceptions.*;
import coreUtil.IRunnable;

import java.util.Scanner;

public class AuthenticationSystem implements IRunnable {
    private LoginHandler loginHandler;
    private SignupHandler signupHandler;
    private AuthenticationUI authUI;

    public AuthenticationSystem(UserManager userManager){
        loginHandler = new LoginHandler(userManager);
        signupHandler = new SignupHandler(userManager);
        authUI = new AuthenticationUI();
    }

    @Override
    public void run() {
        this.authUI.displayWelcomePage();
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        while (option != 1 && option != 2) {
            this.authUI.displayOptionError();
            option = in.nextInt();
        }

        if (option == 1) {
            this.authUI.displayLoginPage();
            this.authUI.displayUsernamePrompt();
            String username = in.nextLine();
            this.authUI.displayPasswordPrompt();
            String password = in.nextLine();
            // TODO: fix this after removing exceptions
            while (true) {
                try {
                    this.loginHandler.loginUser(username, password);
                    break;
                } catch (InvalidUsernameException e) {
                    this.authUI.displayError(e.getMessage());
                    this.authUI.displayUsernamePrompt();
                    username = in.nextLine();
                } catch (InvalidPasswordException e) {
                    this.authUI.displayError(e.getMessage());
                    this.authUI.displayPasswordPrompt();
                    password = in.nextLine();
                }
            }
        } else {
            this.authUI.displaySigningUpPage();
            this.authUI.displayUsernamePrompt();
            String username = in.nextLine();
            this.authUI.displayPasswordPrompt();
            String password = in.nextLine();
            // TODO: fix this after removing exceptions
            while (true) {
                try {
                    this.signupHandler.signUp(name, username, password, "attendee");
                    break;
                } catch (UsernameTakenException e) {
                    this.authUI.displayError(e.getMessage());
                    this.authUI.displayUsernamePrompt();
                    username = in.nextLine();
                } catch (Exception e) {
                    this.authUI.displayError(e.getMessage());
                }
            }
        }
    }
}
