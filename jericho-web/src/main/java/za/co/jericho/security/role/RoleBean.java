package za.co.jericho.security.role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.LogManager;
import org.primefaces.model.DualListModel;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.RoleSearchCriteria;
import za.co.jericho.security.service.ManageSecurityPermissionService;
import za.co.jericho.security.service.ManageSecurityRoleService;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;
import za.co.jericho.util.conversion.CollectionConverter;
import za.co.jericho.util.conversion.CollectionDataConverter;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-31
 */
@ManagedBean(name = "roleBean")
@SessionScoped
public class RoleBean implements Serializable {
    
    private Role role;
    private RoleSearchCriteria roleSearchCriteria = new RoleSearchCriteria();
    private Collection<Role> roles = null;
    @EJB
    private ManageSecurityRoleService manageSecurityRoleService;
    private Collection<Permission> permissionsSource = null;
    private Collection<Permission> permissionsTarget = new ArrayList<>();
    private DualListModel<Permission> permissions;
    @EJB
    private ManageSecurityPermissionService manageSecurityPermissionService;
    
    public RoleBean() {
        
    }
    
    @PostConstruct
    public void init() {
        /* Find the list of permissions */
        permissionsSource = manageSecurityPermissionService.searchAllPermissionsNotDeleted();
        CollectionConverter collectionConverter = new CollectionDataConverter();
        permissions = new DualListModel<Permission>(
            collectionConverter.convertCollectionToList(permissionsSource), 
            collectionConverter.convertCollectionToList(permissionsTarget));
    }

    /* Getters and Setters */
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public RoleSearchCriteria getRoleSearchCriteria() {
        return roleSearchCriteria;
    }

    public void setRoleSearchCriteria(RoleSearchCriteria roleSearchCriteria) {
        this.roleSearchCriteria = roleSearchCriteria;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public ManageSecurityRoleService getManageSecurityRoleService() {
        return manageSecurityRoleService;
    }

    public void setManageSecurityRoleService(ManageSecurityRoleService 
        manageSecurityRoleService) {
        this.manageSecurityRoleService = manageSecurityRoleService;
    }

    public DualListModel<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(DualListModel<Permission> permissions) {
        this.permissions = permissions;
    }
    
    /* Service calls */
    /**
     * For a role that is updated, the source permissions should only include permissions
     * not selected for the role, and the target permissions should contain the 
     * permissions of the selected role.
     */
    public void preparePermissionsForUpdate() {
        LogManager.getRootLogger().info("RoleBean: preparePermissionsForUpdate");
        if (role != null) {
            if (role.getPermissions() != null && role.getPermissions().size() > 0) {
                /* Set permissionsTarget to the current role permissions */
                for (Permission permission: role.getPermissions()) {
                    permissionsTarget.add(permission);
                }
                /* Remove these permissions from permissionsSource */
                Collection<Permission> newPermissionsSource = new ArrayList<>();
                for (Permission sourcePermission: permissionsSource) {
                    boolean present = false;
                    for (Permission existingPermission: role.getPermissions()) {
                        if (Objects.equals(sourcePermission.getId(), existingPermission.getId())) {
                            present = true;
                        }
                    }
                    if (!present) {
                        newPermissionsSource.add(sourcePermission);
                    }
                }
                permissionsSource.clear();
                permissionsSource.addAll(newPermissionsSource);
            }
        }
    }
    
    public Role prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("RoleBean: prepareAdd")
            .toString());
        role = new Role();
        return role;
    }
    
    public void addRole() {
        LogManager.getRootLogger().info("RoleBean: addRole");
        try {
            if (role != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                role.addPermissionCollection(permissions.getTarget());
                role.setCreatedBy(currentUser);
                role.setCreateDate(new Date());
                role = manageSecurityRoleService.addRole(role);
                if (!JsfUtil.isValidationFailed()) {
                    roles = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("RoleAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Role was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateRole() {
        LogManager.getRootLogger().info("RoleBean: updateRole");
        try {
            if (role != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                role.addPermissionCollection(permissionsTarget);
                role.setLastModifiedBy(currentUser);
                role.setLastModifyDate(new Date());
                role = manageSecurityRoleService.updateRole(role);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("RoleUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteRole() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("RoleBean: deleteRole").toString());
        try {
            if (role != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                role.setLastModifiedBy(currentUser);
                role.setLastModifyDate(new Date());
                role = manageSecurityRoleService.markRoleDeleted(role);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("RoleDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    role = null; // Remove selection
                    role = null;
                }
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void searchRoles() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("RoleBean: searchRoles").toString());
        try {
            if (roleSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                roleSearchCriteria.setServiceUser(currentUser);
                roles = manageSecurityRoleService.searchRoles(roleSearchCriteria);
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
}
