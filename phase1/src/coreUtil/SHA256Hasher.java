package coreUtil;
import coreUtil.Hasher;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * A Hasher class which uses SHA256
 * Note: Not in use right now, for phase 2.
 */
public class SHA256Hasher implements Hasher {

    @Override
    public String Hash(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(str.getBytes((StandardCharsets.UTF_8)));
        BigInteger signum = new BigInteger(1, hash);
        return signum.toString(16);
    }

    @Override
    public String Hash(Integer num) throws NoSuchAlgorithmException{
        return Hash(num.toString());
    }
}
