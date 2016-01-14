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
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.audittrail.service.ManageAuditTrailService;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.service.ManageSecurityUserService;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-03
 */
public class AuditTrailInterceptor {
    
    @Resource
    private SessionContext sessionContext;
    @EJB
    private ManageAuditTrailService manageAuditTrailService;
    @EJB
    private ManageSecurityUserService manageSecurityUserService;
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AuditTrailInterceptor - Logging BEFORE calling method ")
            .append(context.getMethod().getName())
            .toString());
        
        Object result = context.proceed();
        
//        /* Get the current logged in user */
//        Principal principal = sessionContext.getCallerPrincipal();
//        LogManager.getRootLogger().info(new StringBuilder("Principal: ")
//            .append(principal.getName())
//            .toString());
//        
//        /* Convert principal to user */
//        User currentUser = manageSecurityUserService.convertPrincipalToUser
//            (principal.getName());
        
        /* Get the method */
        Method method = context.getMethod();
        
        /* Check the annotation */
        if (method.isAnnotationPresent(AuditTrail.class)) {
            Annotation annotation = method.getAnnotation(AuditTrail.class);
            AuditTrail auditTrail = (AuditTrail) annotation;
            manageAuditTrailService.createAuditTrail(auditTrail.serviceName(), 
                result);
        }
        
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AuditTrailInterceptor - Logging AFTER calling method ")
            .append(context.getMethod().getName())
            .toString());
        return result;
    }
    
}
