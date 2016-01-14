package za.co.jericho.exception;

/**
 *
 * @author Jaco Koekemoer
 */
public class LoginUserException extends RuntimeException{
    
    public LoginUserException(String message) {
        super(message);
    }
    
}
