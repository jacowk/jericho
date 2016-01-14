package za.co.jericho.security.permission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import za.co.jericho.security.search.PermissionSearchCriteria;
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
 * Date: 2015-12-05
 */
@ManagedBean(name = "permissionBean")
@SessionScoped
public class PermissionBean implements Serializable {
    
    private Permission permission;
    private PermissionSearchCriteria permissionSearchCriteria = new PermissionSearchCriteria();
    private Collection<Permission> permissions = null;
    @EJB
    private ManageSecurityPermissionService manageSecurityPermissionService;
    private Collection<Role> rolesSource = null;
    private Collection<Role> rolesTarget = new ArrayList<>();
    private DualListModel<Role> roles;
    @EJB
    private ManageSecurityRoleService manageSecurityRoleService;
    
    public PermissionBean() {
        
    }
    
    @PostConstruct
    public void init() {
        /* Find the list of roles */
        rolesSource = manageSecurityRoleService.searchAllRolesNotDeleted();
        CollectionConverter collectionConverter = new CollectionDataConverter();
        roles = new DualListModel<Role>(
            collectionConverter.convertCollectionToList(rolesSource), 
            collectionConverter.convertCollectionToList(rolesTarget));
    }

    /* Getters and Setters */
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public PermissionSearchCriteria getPermissionSearchCriteria() {
        return permissionSearchCriteria;
    }

    public void setPermissionSearchCriteria(PermissionSearchCriteria permissionSearchCriteria) {
        this.permissionSearchCriteria = permissionSearchCriteria;
    }

    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }

    public ManageSecurityPermissionService getManageSecurityPermissionService() {
        return manageSecurityPermissionService;
    }

    public void setManageSecurityPermissionService(ManageSecurityPermissionService 
        manageSecurityPermissionService) {
        this.manageSecurityPermissionService = manageSecurityPermissionService;
    }

    public DualListModel<Role> getRoles() {
        return roles;
    }

    public void setRoles(DualListModel<Role> roles) {
        this.roles = roles;
    }
    
    /* Service calls */
    
    /**
     * For a permission that is updated, the source roles should only include roles
     * not selected for the permission, and the target roles should contain the 
     * roles of the selected permission.
     */
    public void prepareRolesForUpdate() {
        if (permission != null) {
            if (permission.getRoles() != null && permission.getRoles().size() > 0) {
                /* Set rolesTarget to the current permission roles */
                for (Role role: permission.getRoles()) {
                    rolesTarget.add(role);
                }
                /* Remove these roles from rolesSource */
                Collection<Role> newRolesSource = new ArrayList<>();
                for (Role sourceRole: rolesSource) {
                    boolean present = false;
                    for (Role existingRole: permission.getRoles()) {
                        if (Objects.equals(sourceRole.getId(), existingRole.getId())) {
                            present = true;
                        }
                    }
                    if (!present) {
                        newRolesSource.add(sourceRole);
                    }
                }
                rolesSource.clear();
                rolesSource.addAll(newRolesSource);
            }
        }
    }
    
    public Permission prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PermissionBean: prepareAdd")
            .toString());
        /* Create new Permission */
        permission = new Permission();
        return permission;
    }
    
    public void addPermission() {
        try {
            if (permission != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                permission.addRoleCollection(roles.getTarget());
                permission.setCreatedBy(currentUser);
                permission.setCreateDate(new Date());
                permission = manageSecurityPermissionService.addPermission(permission);
                if (!JsfUtil.isValidationFailed()) {
                    permissions = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("PermissionAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Permission was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updatePermission() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PermissionBean: updatePermission").toString());
        try {
            if (permission != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                permission.setRoles(rolesTarget);
                permission.setLastModifiedBy(currentUser);
                permission.setLastModifyDate(new Date());
                permission = manageSecurityPermissionService.updatePermission(permission);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("PermissionUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deletePermission() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PermissionBean: deletePermission").toString());
        try {
            if (permission != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                permission.setLastModifiedBy(currentUser);
                permission.setLastModifyDate(new Date());
                permission = manageSecurityPermissionService.markPermissionDeleted(permission);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("PermissionDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    permission = null; // Remove selection
                    permission = null;
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
    
    public void searchPermissions() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PermissionBean: searchPermissions").toString());
        try {
            if (permissionSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                permissionSearchCriteria.setServiceUser(currentUser);
                permissions = manageSecurityPermissionService.searchPermissions(permissionSearchCriteria);
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
