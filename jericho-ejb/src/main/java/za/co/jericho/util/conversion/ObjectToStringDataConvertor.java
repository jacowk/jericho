package za.co.jericho.util.conversion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.jericho.common.search.AbstractSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-27
 */
public class ObjectToStringDataConvertor implements ObjectToStringConvertor {
    
    @Override
    public String convert(Object object, boolean includeAuditFields) {
        Method[] methods = object.getClass().getMethods();
        StringBuilder stringBuilder = new StringBuilder();
        for (Method method : methods) {
            if (isGetter(method) && !isNonRelevantMethod(method)) {
                if (!includeAuditFields && isAuditMethod(method)) {
                    continue;
                }
                org.apache.log4j.Logger.getRootLogger()
                    .info(new StringBuilder("ObjectToStringDataConvertor: ")
                            .append("Class: ")
                            .append(object.getClass().getName())
                            .append(", Method: ")
                    .append(method.getName())
                    .toString());
                try {
                    Object result = method.invoke(object);
                    if (result != null) {
                        stringBuilder.append("\n");
                        stringBuilder.append(method.getName());
                        stringBuilder.append(": ");
                        stringBuilder.append(result.toString());
                    }
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(AbstractSearchCriteria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return stringBuilder.toString();
    }
    
    private boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        } 
        if (void.class.equals(method.getReturnType())) {
            return false;
        }
        return true;
    }
    
    private boolean isAuditMethod(Method method) {
        return method.getName().startsWith("getCreatedBy") ||
            method.getName().startsWith("getCreateDate") ||
            method.getName().startsWith("getLastModifiedBy") ||
            method.getName().startsWith("getLastModifyDate");
    }
    
    private boolean isNonRelevantMethod(Method method) {
        return method.getName().startsWith("getClass");
    }
    
}
