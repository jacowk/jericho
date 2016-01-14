package za.co.jericho.exception;

/**
 *
 * @author @author Jaco Koekemoer
 * Date: 2015-07-18
 * 
 */
public class DeleteNotSupportedException extends RuntimeException {
    
    public DeleteNotSupportedException(String message) {
        super(message);
    }
    
}
