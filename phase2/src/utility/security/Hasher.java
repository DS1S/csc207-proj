package utility.security;

import java.security.NoSuchAlgorithmException;

/**
 * A interface to which Hasher classes must implement.
 * Note: Not in use right now, for phase 2.
 */
public interface Hasher {
    String Hash (String str) throws NoSuchAlgorithmException;
    String Hash (Integer num) throws NoSuchAlgorithmException;
}
