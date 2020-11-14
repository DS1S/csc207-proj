package LoginSystem;

import coreUtil.IRunnable;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AuthenticationSystem implements IRunnable {
    private final AuthenticationUI authUI;
    private final LoginSystem loginSystem;
    private final SignupSystem signupSystem;
    private Scanner scanner = new Scanner(System.in);

    public AuthenticationSystem(UserManager userManager){
        authUI = new AuthenticationUI();
        loginSystem = new LoginSystem(userManager);

        String ATTENDEE_TYPE = "attendee";
        signupSystem = new SignupSystem(userManager, ATTENDEE_TYPE);
    }

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
