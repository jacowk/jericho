package za.co.jericho.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import za.co.jericho.security.ServiceName;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-30
 * 
 * http://www.mkyong.com/java/java-custom-annotations-example/
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityPermission {
    
    ServiceName serviceName() default ServiceName.NONE;
    
}
