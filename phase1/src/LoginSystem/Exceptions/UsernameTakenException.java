package LoginSystem.Exceptions;

public class UsernameTakenException extends  Exception {
    String e;
    public UsernameTakenException() {
        this.e = "This username has been taken";
    }
}
