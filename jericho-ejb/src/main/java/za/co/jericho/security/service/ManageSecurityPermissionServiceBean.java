package za.co.jericho.security.service;

import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.LogManager;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.search.AllPermissionsNotDeletedSearchUnit;
import za.co.jericho.security.search.PermissionConverterSearchUnit;
import za.co.jericho.security.search.PermissionSearchUnit;
import za.co.jericho.security.search.PermissionSearchCriteria;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

@Stateless
@Remote(ManageSecurityPermissionService.class)
public class ManageSecurityPermissionServiceBean extends AbstractServiceBean 
implements ManageSecurityPermissionService{

    @Override
    public Permission addPermission(Permission permission) {
        /* Validations */
        /* State validation */
        permission.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(permission)) {
            getEntityManager().persist(permission);
        }
        return permission;
    }

    @Override
    public Permission updatePermission(Permission permission) {
        /* Validations */
        /* State validation */
        permission.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(permission)) {
            getEntityManager().merge(permission);
        }
        return permission;
    }

    @Override
    public Permission markPermissionDeleted(Permission permission) {
        throw new DeleteNotSupportedException("Deleting a permission is not supported");
        
//        EntityValidator entityValidator = new EntityStateValidator();
//        if (entityValidator.isValidateEntityBeforeUpdate(permission)) {
//            permission.setDeleted(true);
//            permission = updatePermission(permission);
//        }
//        return permission;
    }

    @Override
    public Collection<Permission> searchPermissions(PermissionSearchCriteria 
        permissionSearchCriteria) {
        PermissionSearchUnit permissionsSearchUnit = new 
            PermissionSearchUnit(getEntityManager(), permissionSearchCriteria);
        permissionsSearchUnit.run();
        return permissionsSearchUnit.getPermissions();
    }
    
    @Override
    public Collection<Permission> searchAllPermissionsNotDeleted() {
        AllPermissionsNotDeletedSearchUnit allPermissionsNotDeletedSearchUnit = new
            AllPermissionsNotDeletedSearchUnit(getEntityManager());
        allPermissionsNotDeletedSearchUnit.run();
        return allPermissionsNotDeletedSearchUnit.getPermissions();
    }

    @Override
    public Permission findPermission(Object id) {
        return (Permission) getEntityManager().find(Permission.class, id);
    }
    
    @Override
    public Collection<Permission> findAllPermissions() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Permission.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    @Override
    public Permission searchConverterPermission(String permissionName) {
        PermissionConverterSearchUnit permissionConverterSearchUnit = new
            PermissionConverterSearchUnit(getEntityManager(), permissionName);
        permissionConverterSearchUnit.run();
        return permissionConverterSearchUnit.getPermission();
    }

}