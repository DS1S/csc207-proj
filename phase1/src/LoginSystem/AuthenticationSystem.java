package LoginSystem;

import Presenters.AuthenticationUI;
import coreUtil.IRunnable;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class to handle logging in of existing users and signing up of new ones.
 */
public class AuthenticationSystem implements IRunnable {
    private final AuthenticationUI authUI;
    private final LoginSystem loginSystem;
    private final SignupSystem signupSystem;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Creates a new AuthenticationSystem to use the input UserManager to do it's duties.
     * @param userManager the UserManager to work with.
     */
    public AuthenticationSystem(UserManager userManager){
        authUI = new AuthenticationUI();
        loginSystem = new LoginSystem(userManager);

        String ATTENDEE_TYPE = "attendee";
        signupSystem = new SignupSystem(userManager, ATTENDEE_TYPE);
    }

    /**
     * The run method implementation required by IRunnable. Handles user input for choosing between login and signup.
     */
    @Override
    public void run() {
        int option = 0;
        while (option != 1 && option != 2) {
            this.authUI.displayWelcomePage();
            try{
                option = scanner.nextInt();
                processInput(option);
            }
            catch (InputMismatchException e){
                this.authUI.displayOptionError();
                scanner.nextLine();

            }
        }
    }

    /**
     * Processes the user's input once it's verified.
     * @param input the verified input of the user.
     */
    private void processInput(int input){
        switch (input) {
            case (1):
                loginSystem.run();
                break;
            case (2):
                signupSystem.run();
                loginSystem.run();
                break;
        }
    }
}
