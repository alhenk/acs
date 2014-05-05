package kz.trei.acs.util;

import org.apache.log4j.Logger;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public final class Administer {
    private static final Logger LOGGER = Logger.getLogger(Administer.class);

    private Administer() {
    }

    public static String getSecurePassword(String passwordToHash, String salt) throws NoSuchAlgorithmException {
        String generatedPassword;
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        //messageDigest.update(salt.getBytes());
        byte[] bytes = messageDigest.digest(passwordToHash.getBytes(Charset.forName("UTF-8")));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPassword = sb.toString();
        LOGGER.debug("Password : " + passwordToHash + "; Hash : " + generatedPassword);
        return generatedPassword;
    }

    public static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        LOGGER.debug("Salt : " + salt.toString());
        return salt.toString();
    }
}
