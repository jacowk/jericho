package za.co.jericho.exception;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-01-23
 */
public class NonNumericStringException extends RuntimeException {
    
    public NonNumericStringException(String message) {
        super(message);
    }
    
}
