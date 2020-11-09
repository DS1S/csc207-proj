import CoreEntities.User;
import FileHandleSystem.FileHandler;
import LoginSystem.AuthenticationUI;
import LoginSystem.AuthenticationSystem;
import LoginSystem.UserManager;

public class TechConferenceMain {
    public static void main(String[] args) {
        try {
            FileHandler<User> fh = new FileHandler<>("/home/vedang/Desktop/UofT/Courses/Year 2/CSC207/group_0025/phase1/database/Users.ser");
            UserManager um = new UserManager(fh.loadCollection());
            AuthenticationSystem as = new AuthenticationSystem(um);
            AuthenticationUI ap = new AuthenticationUI(as);

            ap.displayWelcomePage();

//            Scanner in = new Scanner(System.in);
//            User loggedInUser = null;
//            while (loggedInUser == null) {
//                System.out.print("Enter your username: ");
//                String username = in.nextLine();
//                System.out.print("Enter your password: ");
//                String password = in.nextLine();
//
//                loggedInUser = lh.loginUser(username, password);
//            }
//            System.out.println("Welcome, " + loggedInUser.getUsername());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
