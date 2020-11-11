import CoreEntities.Users.User;
import FileHandleSystem.FileSerializer;
import LoginSystem.AuthenticationUI;
import LoginSystem.AuthenticationSystem;
import LoginSystem.UserManager;

public class TechConferenceMain {
    public static void main(String[] args) {
        try {
            FileSerializer<User> fh = new FileSerializer<>("/home/vedang/Desktop/UofT/Courses/Year 2/CSC207/group_0025/phase1/database/Users.ser");
            UserManager um = new UserManager(fh.loadCollection());
            AuthenticationSystem as = new AuthenticationSystem(um);
            AuthenticationUI ap = new AuthenticationUI(as);

            ap.displayWelcomePage();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
