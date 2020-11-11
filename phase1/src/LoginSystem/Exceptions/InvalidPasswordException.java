package LoginSystem.Exceptions;

public class InvalidPasswordException extends  Exception {
    String e;
    public InvalidPasswordException() {
        this.e = "This password does not match.";
    }
}
