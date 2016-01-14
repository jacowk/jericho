package za.co.jericho.exception;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-14
 */
public class PermissionCheckException extends RuntimeException {
    
    public PermissionCheckException() {
        super();
    }
    
    public PermissionCheckException(String message) {
        super(message);
    }
    
}
