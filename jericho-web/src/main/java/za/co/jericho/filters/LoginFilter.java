package za.co.jericho.filters;

import java.io.IOException;
import java.security.Principal;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.audit.service.ManageAuditActivityService;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.service.ManageSecurityUserService;
import za.co.jericho.session.SessionServices;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-06
 */
public class LoginFilter implements Filter {

    private User currentUser;
    @EJB
    private ManageAuditActivityService manageAuditActivityService;
    @EJB
    private ManageSecurityUserService manageSecurityUserService;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
        FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        Principal principal = httpServletRequest.getUserPrincipal();
        String username = principal.getName();
        LogManager.getRootLogger().info(new StringBuilder()
                .append("LoginFilter: username: ")
                .append(username)
                .toString());
        storeUserOnSession(httpServletRequest, username);
        logAuditActivity();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
    private void storeUserOnSession(HttpServletRequest httpServletRequest, String username) {
        currentUser = manageSecurityUserService.convertPrincipalToUser(username);
        SessionServices sessionServices = new SessionServices();
        sessionServices.setUserOnSession(httpServletRequest, currentUser);
    }
    
    /**
     * This method is duplicated in PrincipalToUserConverter and SecurityPermissionInterceptor
     * 
     * @param username
     * @return 
     */
    /*public User convert(String username) {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PrincipalToUserConverter: convert: ")
            .append(username)
            .toString());
        // Find user, and place user on session
        UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
        userSearchCriteria.setUsername(username);
        userSearchCriteria.setSearchForLogin(true); // In order to prevent initial permission checks
        Collection<User> users = new ArrayList<>();
        users = manageSecurityUserService.searchUsers(userSearchCriteria);
        if (users != null) {
            if (users.size() == 1) {
                currentUser = users.iterator().next();
                Collection<Role> roles = currentUser.getRoles();
                if (roles != null) {
                    for (Role role: roles) {
                        LogManager.getRootLogger().info(new StringBuilder()
                            .append("Role: ")
                            .append(role.getName())
                            .toString());
                    }
                }
                else {
                    LogManager.getRootLogger().info(new StringBuilder()
                        .append("No roles defined for user")
                        .toString());
                }
                    
                LogManager.getRootLogger().info(new StringBuilder()
                    .append("LoginFilter: storeUserOnSession: ")
                    .append("User found: ")
                    .append(currentUser.getId())
                    .toString());
            }
            else if (users.size() > 1) {
                throw new PrincipalToUserConversionException("Multiple users found for username " + username);
            }
            else {
                throw new PrincipalToUserConversionException("Username not found: " + username);
            }
        }
        else {
            throw new PrincipalToUserConversionException("User list retrieved at login is null for username " + username);
        }
        return currentUser;
    }*/
    
    private void logAuditActivity() {
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.SECURITY_USER, //String entityName
            "", //String serviceName
            AuditActivityType.LOGIN, //String activityType
            "User logged in", //String description
            currentUser)); //User currentUser
    }
    
}
