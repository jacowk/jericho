package za.co.jericho.interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.apache.log4j.LogManager;
import za.co.jericho.annotations.UserActivityMonitor;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.PermissionSearchCriteria;
import za.co.jericho.security.service.ManageSecurityPermissionService;
import za.co.jericho.security.service.ManageSecurityUserService;
import za.co.jericho.useractivity.domain.UserActivity;
import za.co.jericho.useractivity.service.ManageUserActivityService;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-04
 */
public class UserActivityMonitorInterceptor {
    
    @Resource
    private SessionContext sessionContext;
    @EJB
    private ManageSecurityUserService manageSecurityUserService;
    @EJB
    private ManageSecurityPermissionService manageSecurityPermissionService;
    @EJB
    private ManageUserActivityService manageUserActivityService;
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("UserActivityMonitorInterceptor - Logging BEFORE calling method ")
            .append(context.getTarget().getClass().getName())
            .append(": ")
            .append(context.getMethod().getName())
            .toString());
        
        Object result = context.proceed();
        /* First call the method, then do auditing */
        
        /* Get the current logged in user */
        Principal principal = sessionContext.getCallerPrincipal();
        LogManager.getRootLogger().info(new StringBuilder("Principal: ")
            .append(principal.getName())
            .toString());
        
        /* Convert principal to user */
        User currentUser = manageSecurityUserService.convertPrincipalToUser
            (principal.getName());
        
        /* Get the method */
        Method method = context.getMethod();
        
        /* Check the annotation */
        if (method.isAnnotationPresent(UserActivityMonitor.class)) {
            Annotation annotation = method.getAnnotation(UserActivityMonitor.class);
            UserActivityMonitor userActivityMonitor = (UserActivityMonitor) annotation;
            UserActivity userActivity = createUserActivity(currentUser, result, 
                userActivityMonitor.serviceName().getValue());
//            getSearchCriteria(userActivity, userActivityMonitor.serviceName());
            manageUserActivityService.addUserActivity(userActivity);
        }
        
        LogManager.getRootLogger().info(new StringBuilder()
            .append("UserActivityMonitorInterceptor - Logging AFTER calling method ")
            .append(context.getTarget().getClass().getName())
            .append(": ")
            .append(context.getMethod().getName())
            .toString());
        return result;
    }
    
    /**
     * Create user activity
     * 
     * @return UserActivity
     */
    private UserActivity createUserActivity(User currentUser, 
        Object result, String serviceName) {
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase(serviceName);
        UserActivity userActivity = new UserActivity();
        userActivity.setActivityDate(new Date());
        userActivity.setActivityUser(currentUser);
        Permission permission = findPermission(serviceName);
        userActivity.setPermission(permission);
        userActivity.setDescription(convertedServiceName);
        if (result instanceof AbstractEntity) {
            AbstractEntity abstractEntity = (AbstractEntity) result;
            userActivity.setEntityId(abstractEntity.getId());
        }
        return userActivity;
    }
    
    /**
     * Find permission. The activity of a user is tracked according to the permissions
     * the user has in the system.
     * 
     * @param serviceName String
     * @return Permission
     */
    private Permission findPermission(String serviceName)
    {
        PermissionSearchCriteria permissionSearchCriteria = new 
            PermissionSearchCriteria();
        permissionSearchCriteria.setServiceName(serviceName);
        Collection<Permission> permissions = manageSecurityPermissionService
            .searchPermissions(permissionSearchCriteria);
        if (permissions != null && permissions.size() > 0) {
            return permissions.iterator().next();
        }
        return null;
    }
    
//    /**
//     * 
//     * @param method
//     * @param userActivity
//     * @param serviceName 
//     */
//    private void getSearchCriteria(Method method, UserActivity userActivity, 
//        ServiceName serviceName) {
//        if (serviceName.getValue().contains("Search")) {
//            Class[] parameterTypes = method.getParameterTypes();
//            for (Class clazz: parameterTypes) {
//                if (clazz instanceof AttorneySearchCriteria) {
//                    AttorneySearchCriteria attorneySearchCriteria = (AttorneySearchCriteria) clazz;
//                    StringBuilder description = new StringBuilder(userActivity.getDescription());
//                    description.append(" - ");
//                    description.append(attorneySearchCriteria.toString());
//                    userActivity.setDescription(description.toString());
//                }
//            }
//        }
//    }
    
}
