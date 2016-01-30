package za.co.jericho.security.encryption;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is used to encrypt, using SHA-256 encryption
 * @author Jaco Koekemoer
 */
public class SHA256HashGenerator implements HashGenerator {
    
    @Override
    public String generateHash(String clearText) {
        String cipherText = null;
        if (clearText != null)
        {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(clearText.getBytes("UTF-8")); // Change this to "UTF-16" if needed
                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                cipherText = bigInt.toString(16);
            }
            catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        return cipherText;
    }
    
}
