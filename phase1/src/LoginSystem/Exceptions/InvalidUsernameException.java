package LoginSystem.Exceptions;

public class InvalidUsernameException extends Exception {
    String e;
    public InvalidUsernameException() {
        this.e = "This username has a duplicate.";
    }
}
