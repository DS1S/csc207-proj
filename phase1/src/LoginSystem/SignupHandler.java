package LoginSystem;
import CoreEntities.User;

public class SignupHandler {

    public SignupHandler(){}

    public boolean SignUp(String username, String password, User.AccountType type, UserManager userManager){
        if (userManager.getUserWithUsername(username) != null){
            // TODO: contact presenter
            System.out.println("Username is Taken!");
            return false;
        }

        User u = new User(username, password, type);
        if (userManager.getUserWithUUID(u.GetUUID()) != null){
            // TODO: maybe create an exception for this case?
            System.out.println("ERROR: Duplicate UUID");
            return false;
        }
        userManager.addUser(u);
        // TODO: contact presenter
        return true;
    }

}
