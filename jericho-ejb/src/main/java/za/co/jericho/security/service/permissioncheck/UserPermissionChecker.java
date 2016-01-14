package za.co.jericho.security.service.permissioncheck;

import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import org.apache.log4j.LogManager;
import za.co.jericho.common.search.SearchType;
import za.co.jericho.exception.PermissionCheckException;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.PermissionSearchCriteria;
import za.co.jericho.security.service.ManageSecurityPermissionService;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
public class UserPermissionChecker implements PermissionChecker {
    
    @EJB
    private ManageSecurityPermissionService manageSecurityPermissionService; //This is null during runtime

    @Override
    public boolean check(User user, String permissionServiceName) {
        if (user == null) {
            throw new PermissionCheckException("Unable to check permission. "
                + "User not provided.");
        }
        if (permissionServiceName == null || permissionServiceName.length() == 0) {
            throw new PermissionCheckException("Unable to check permission. "
                + "Permission name not provided.");
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new PermissionCheckException("Unable to check permission. "
                + "User does not have roles defined. User: " + user.getId());
        }
//        checkPermissionServiceName(user, permissionServiceName);
        boolean permissionPresent = false;
        for (Role role: user.getRoles()) {
            if (role.getPermissions() != null) {
                for (Permission permission: role.getPermissions()) {
                    if (permission.getServiceName().equals(permissionServiceName)) {
                        permissionPresent = true;
                    }
                }
            }
        }
        if (!permissionPresent) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("User ");
            stringBuilder.append(user.getFirstname());
            stringBuilder.append(" ");
            stringBuilder.append(user.getSurname());
            stringBuilder.append(" does not have permission for this action");
            throw new PermissionCheckException(stringBuilder.toString());
        }
        return permissionPresent;
    }
    
    /**
     * A convenience method for adding permissions which does not exist in the system
     * 
     * @param user User
     * @param permissionServiceName String
     */
    private void checkPermissionServiceName(User user, String permissionServiceName) {
        LogManager.getRootLogger().info(new StringBuilder()
                .append("UserPermissionChecker: checkPermissionServiceName")
                .append(permissionServiceName)
                .toString());
        PermissionSearchCriteria permissionSearchCriteria = new PermissionSearchCriteria();
        permissionSearchCriteria.setServiceName(permissionServiceName);
        permissionSearchCriteria.setSearchType(SearchType.EQUALS);
        Collection<Permission> permissions = manageSecurityPermissionService
            .searchPermissions(permissionSearchCriteria);
        if (permissions == null || permissions.size() < 1) {
            Permission permission = new Permission();
            permission.setName(permissionServiceName + " (Added in PermissionChecker)");
            permission.setServiceName(permissionServiceName);
            permission.setCreateDate(new Date());
            permission.setCreatedBy(user);
            manageSecurityPermissionService.addPermission(permission);
        }
    }
    
}
