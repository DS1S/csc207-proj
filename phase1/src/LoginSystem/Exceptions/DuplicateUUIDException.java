package LoginSystem.Exceptions;

public class DuplicateUUIDException extends Exception {
    String e;
    public DuplicateUUIDException() {
        this.e = "This UUID has a duplicate.";
    }

}
