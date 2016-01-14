package za.co.jericho.security.service;

import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.search.PermissionSearchCriteria;

/**
 *
 * @author user
 */
@Remote
public interface ManageSecurityPermissionService {
    
    public Permission addPermission(Permission permission);

    public Permission updatePermission(Permission permission);

    public Permission markPermissionDeleted(Permission permission);

    public Collection<Permission> searchPermissions(PermissionSearchCriteria permissionSearchCriteria);
    
    public Collection<Permission> searchAllPermissionsNotDeleted();
    
    public Permission findPermission(Object id);
    
    public Collection<Permission> findAllPermissions();
    
    public Permission searchConverterPermission(String permissionName);
    
}
