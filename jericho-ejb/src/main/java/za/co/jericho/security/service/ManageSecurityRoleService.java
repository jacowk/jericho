package za.co.jericho.security.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.RoleSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-31
 */
@Remote
public interface ManageSecurityRoleService {
    
    public Role addRole(Role role);

    public Role updateRole(Role role);

    public Role markRoleDeleted(Role role);

    public List<Role> searchRoles(RoleSearchCriteria roleSearchCriteria);
    
    public List<Role> searchAllRolesNotDeleted();
    
    public Role findRole(Object id); 
    
    public List<Role> findAllRoles();
    
    public Role searchConverterRole(String roleName);

    public Role addPermissionToRole(Role role, Permission permission);

    public Role removePermissionFromRole(Role role, Permission permission);

    public List<Permission> listPermissionsForRole(Role role);

    public List<Role> listRolesForPermission(Permission permission);

    public Role addUserToRole(Role role, User user);

    public Role removeUserFromRole(Role role, User user);

    public List<Role> listRolesForUser(User user);

    public List<User> listUsersForRole(Role role);
    
}
