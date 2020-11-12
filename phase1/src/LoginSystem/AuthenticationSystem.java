package LoginSystem;

import CoreEntities.Users.User;
import coreUtil.IRunnable;

import java.util.Scanner;

public class AuthenticationSystem implements IRunnable {
    private AuthenticationUI authUI;
    private UserManager um;
    private LoginSystem loginSystem;
    private SignupSystem signupSystem;

    private final String ATTENDEE_TYPE = "attendee";

    public AuthenticationSystem(UserManager userManager){
        this.um = userManager;
        authUI = new AuthenticationUI();
        loginSystem = new LoginSystem(um);
        signupSystem = new SignupSystem(um, ATTENDEE_TYPE);
    }

    @Override
    public void run() {
        this.authUI.displayWelcomePage();
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        while (option != 1 && option != 2) {
            this.authUI.displayOptionError();
            option = scanner.nextInt();
        }
        scanner.close();

        switch (option) {
            case (1) -> loginSystem.run();
            case (2) -> {
                signupSystem.run();
                run();
            }
        }
    }
}
