import CoreEntities.User;
import FileHandleSystem.FileHandler;
import LoginSystem.LoginHandler;
import LoginSystem.UserManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TechConferenceMain {
    public static void main(String[] args) {
        try {
            FileHandler<User> fh = new FileHandler<>("path/to/group_0025/phase1/database/Users.ser");
            UserManager um = new UserManager(fh.loadCollection());
            LoginHandler lh = new LoginHandler(um);

            Scanner in = new Scanner(System.in);
            User loggedInUser = null;
            while (loggedInUser == null) {
                System.out.print("Enter your username: ");
                String username = in.nextLine();
                System.out.print("Enter your password: ");
                String password = in.nextLine();

                loggedInUser = lh.loginUser(username, password);
            }
            System.out.println("Welcome, " + loggedInUser.getUsername());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
