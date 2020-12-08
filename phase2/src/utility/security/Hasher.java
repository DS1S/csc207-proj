package utility.security;

import java.security.NoSuchAlgorithmException;

/**
 * An interface to which Hasher classes must implement.
 */
public interface Hasher {
    String Hash (String str) throws NoSuchAlgorithmException;
    String Hash (Integer num) throws NoSuchAlgorithmException;
}
