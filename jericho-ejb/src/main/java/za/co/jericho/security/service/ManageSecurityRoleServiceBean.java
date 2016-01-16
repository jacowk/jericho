package za.co.jericho.security.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.LogManager;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.AllRolesNotDeletedSearchUnit;
import za.co.jericho.security.search.RoleConverterSearchUnit;
import za.co.jericho.security.search.RoleSearchCriteria;
import za.co.jericho.security.search.RoleSearchUnit;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

@Stateless
@Remote(ManageSecurityRoleService.class)
public class ManageSecurityRoleServiceBean extends AbstractServiceBean 
implements ManageSecurityRoleService {

    @Override
    public Role addRole(Role role) {
        /* Validations */
        /* State validation */
        role.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(role)) {
            getEntityManager().persist(role);
        }
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        LogManager.getRootLogger().info("ManageSecurityRoleServiceBean: updateRole");
        
        /* Validations */
        /* State validation */
        role.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(role)) {
            getEntityManager().merge(role);
        }
        return role;
    }

    @Override
    public Role markRoleDeleted(Role role) {
        throw new DeleteNotSupportedException("Deleting a role is not supported");
    }

    @Override
    public List<Role> searchRoles(RoleSearchCriteria roleSearchCriteria) {
        RoleSearchUnit rolesSearchUnit = new RoleSearchUnit(getEntityManager(),
            roleSearchCriteria);
        rolesSearchUnit.run();
        return rolesSearchUnit.getRoles();
    }
    
    @Override
    public List<Role> searchAllRolesNotDeleted() {
        AllRolesNotDeletedSearchUnit allRolesNotDeletedSearchUnit = new
            AllRolesNotDeletedSearchUnit(getEntityManager());
        allRolesNotDeletedSearchUnit.run();
        return allRolesNotDeletedSearchUnit.getRoles();
    }
    
    @Override
    public Role findRole(Object id) {
        return getEntityManager().find(Role.class, id);
    }
    
    @Override
    public List<Role> findAllRoles() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Role.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Role searchConverterRole(String roleName) {
        RoleConverterSearchUnit roleConverterSearchUnit = new 
            RoleConverterSearchUnit(getEntityManager(), roleName);
        roleConverterSearchUnit.run();
        return roleConverterSearchUnit.getRole();
    }
    
    /**
     * 
     * @param role
     * @param permission
     */
    public Role addPermissionToRole(Role role, Permission permission) {
        return null;
    }

    /**
     * 
     * @param role
     * @param permission
     */
    public Role removePermissionFromRole(Role role, Permission permission) {
        return null;
    }

    /**
     * 
     * @param role
     */
    public List<Permission> listPermissionsForRole(Role role) {
        return null;
    }

    /**
     * 
     * @param permission
     */
    public List<Role> listRolesForPermission(Permission permission) {
        return null;
    }

    /**
     * 
     * @param role
     * @param user
     */
    public Role addUserToRole(Role role, User user) {
        return null;
    }

    /**
     * 
     * @param role
     * @param user
     */
    public Role removeUserFromRole(Role role, User user) {
        return null;
    }

    /**
     * 
     * @param user
     */
    public List<Role> listRolesForUser(User user) {
        return null;
    }

    /**
     * 
     * @param role
     */
    public List<User> listUsersForRole(Role role) {
        return null;
    }

}