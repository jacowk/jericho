package za.co.jericho.interceptors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import za.co.jericho.audit.service.ManageAuditActivityService;
import za.co.jericho.security.domain.User;

/**
 * This interceptor is used to perform audit activity logging.
 * 
 * @author Jaco Koekemoer
 * Date: 2015-07-13
 */
public class AuditActivityInterceptor {
    
    @EJB
    private ManageAuditActivityService manageAuditActivityService;
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        System.out.println("AuditActivityInterceptor - Logging BEFORE calling "
            + "method " + context.getMethod().getName());
        
        Object result = context.proceed();
        /* First call the method, then do auditing */
        
        Object[] parameters = context.getParameters();
        String className = context.getClass().getName();
        String methodName = context.getMethod().getName();
        
        System.out.println("AuditActivityInterceptor - Logging AFTER calling "
            + "method " + context.getMethod().getName());
        return result;
    }
    
}
