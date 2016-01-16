package za.co.jericho.security.permission;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.LogManager;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.PermissionSearchCriteria;
import za.co.jericho.security.service.ManageSecurityPermissionService;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

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
    
    public PermissionBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
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
    
    /* Service calls */
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
                permissions = manageSecurityPermissionService.searchPermissions(
                    permissionSearchCriteria);
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
