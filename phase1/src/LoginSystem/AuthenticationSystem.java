package LoginSystem;

import java.util.UUID;
import CoreEntities.User;

public class AuthenticationSystem {
    UserManager userManager;
    LoginHandler loginHandler;
    SignupHandler signupHandler;


    public AuthenticationSystem(){
        userManager = new UserManager();
        loginHandler = new LoginHandler();
        signupHandler = new SignupHandler();
    }

    public AuthenticationSystem(UserManager userManager){
        this.userManager = userManager;
        loginHandler = new LoginHandler();
        signupHandler = new SignupHandler();
    }

    public boolean SignUp(String username, String password, User.AccountType type){
        return signupHandler.SignUp(username, password, type, userManager);
    }

    public User LogIn(String username, String password){
        return loginHandler.loginUser(username, password, userManager);
    }
}
