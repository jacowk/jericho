package za.co.jericho.security.encryption;

/**
 * This interface defines services for generating hash encryption, used for 
 * encrypting passwords.
 * 
 * @author Jaco Koekemoer
 */
public interface HashGenerator {
    
    public String generateHash(String clearText);
    
}
