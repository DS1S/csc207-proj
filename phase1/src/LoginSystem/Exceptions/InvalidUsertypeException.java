package LoginSystem.Exceptions;

public class InvalidUsertypeException extends Exception {
    String e;
    public InvalidUsertypeException() {
        this.e = "This usertype is invalid.";
    }
}
