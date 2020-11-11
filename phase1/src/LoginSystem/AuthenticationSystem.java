package LoginSystem;

public class AuthenticationSystem {
    private LoginHandler loginHandler;
    private SignupHandler signupHandler;

    public AuthenticationSystem(UserManager userManager){
        loginHandler = new LoginHandler(userManager);
        signupHandler = new SignupHandler(userManager);
    }

    public void signUp(String username, String password) throws UsernameTakenException, DuplicateUUIDException {
        signupHandler.signUp(username, password);
    }

    public void LogIn(String username, String password) throws InvalidPasswordException, InvalidUsernameException {
        loginHandler.loginUser(username, password);
    }
}
