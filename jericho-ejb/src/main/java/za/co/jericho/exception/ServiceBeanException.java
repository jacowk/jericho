package za.co.jericho.exception;

/**
 *
 * @author Jaco Koekemoer
 */
public class ServiceBeanException extends RuntimeException {
    
    public ServiceBeanException() {
        super();
    }
    
    public ServiceBeanException(String message) {
        super(message);
    }
    
}
