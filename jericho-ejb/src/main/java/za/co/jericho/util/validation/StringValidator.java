package za.co.jericho.util.validation;

/**
 *
 * @author user
 */
public interface StringValidator {
    
    public boolean isNull(String data);
    
    public boolean isNullOrEmpty(String data);
    
    public boolean isNumeric(String data);
    
}
