package za.co.jericho.util.conversion;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-27
 */
public interface ObjectToStringConvertor {
    
    public String convert(Object object, boolean includeAuditFields);
    
}
