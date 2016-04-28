package za.co.jericho.interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.Principal;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.apache.log4j.LogManager;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.service.ManageSecurityUserService;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;

/**
 * This interceptor is to check if a user has permission to perform a particular
 * activity
 * 
 * http://howtodoinjava.com/2014/06/09/complete-java-annotations-tutorial/#create_custom_annotations
 * 
 * @author Jaco Koekemoer
 * Date: 2015-07-13
 */
public class SecurityPermissionInterceptor {
    
    @Resource
    private SessionContext sessionContext;
    @EJB
    private ManageSecurityUserService manageSecurityUserService;
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SecurityPermissionInterceptor - Logging BEFORE calling method ")
            .append(context.getTarget().getClass().getName())
            .append(": ")
            .append(context.getMethod().getName())
            .toString());
        /* Get the current logged in user */
        Principal principal = sessionContext.getCallerPrincipal();
        LogManager.getRootLogger().info(new StringBuilder("Principal: ")
            .append(principal.getName())
            .toString());
        
        /* Convert principal to user */
        User currentUser = manageSecurityUserService.convertPrincipalToUser(
            principal.getName());
        
        /* Get the method */
        Method method = context.getMethod();
        
        /* Check the annotation */
        if (method.isAnnotationPresent(SecurityPermission.class)) {
            Annotation annotation = method.getAnnotation(SecurityPermission.class);
            SecurityPermission securityPermission = (SecurityPermission) annotation;
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(currentUser, securityPermission.serviceName().getValue());
        }
        
        Object result = context.proceed();
        
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SecurityPermissionInterceptor - Logging AFTER calling method ")
            .append(context.getTarget().getClass().getName())
            .append(": ")
            .append(context.getMethod().getName())
            .toString());
        return result;
    }
    
}
