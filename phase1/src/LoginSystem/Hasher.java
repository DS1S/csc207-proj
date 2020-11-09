package LoginSystem;

import java.security.NoSuchAlgorithmException;

public interface Hasher {
    String Hash (String str) throws NoSuchAlgorithmException;
    String Hash (Integer num) throws NoSuchAlgorithmException;
}
