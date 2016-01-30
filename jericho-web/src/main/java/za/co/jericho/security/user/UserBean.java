package za.co.jericho.security.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.LogManager;
import za.co.jericho.common.search.SearchType;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.RoleSearchCriteria;
import za.co.jericho.security.search.UserSearchCriteria;
import za.co.jericho.security.service.ManageSecurityRoleService;
import za.co.jericho.security.service.ManageSecurityUserService;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-05
 */
@ManagedBean(name = "userBean")
@javax.faces.bean.SessionScoped
public class UserBean implements Serializable {
    
    private User user;
    private UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
    private Collection<User> users = null;
    @EJB
    private ManageSecurityUserService manageSecurityUserService;
    @EJB
    private ManageSecurityRoleService manageSecurityRoleService;
    private String confirmPassword = "";
    private String userExistsMessage = "";
    private String emailExistsMessage = "";
    private Long selectedRoleId; /* Need this for combobox selections */
    private Role selectedRole; /* This is purely for display on the view screen */
    
    public UserBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSearchCriteria getUserSearchCriteria() {
        return userSearchCriteria;
    }

    public void setUserSearchCriteria(UserSearchCriteria userSearchCriteria) {
        this.userSearchCriteria = userSearchCriteria;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public ManageSecurityUserService getManageSecurityUserService() {
        return manageSecurityUserService;
    }

    public void setManageSecurityUserService(ManageSecurityUserService manageSecurityUserService) {
        this.manageSecurityUserService = manageSecurityUserService;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserExistsMessage() {
        return userExistsMessage;
    }

    public void setUserExistsMessage(String userExistsMessage) {
        this.userExistsMessage = userExistsMessage;
    }

    public String getEmailExistsMessage() {
        return emailExistsMessage;
    }

    public void setEmailExistsMessage(String emailExistsMessage) {
        this.emailExistsMessage = emailExistsMessage;
    }

    public Long getSelectedRoleId() {
        return selectedRoleId;
    }

    public void setSelectedRoleId(Long selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }
    
    /* Service calls */
    public User prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("UserBean: prepareAdd")
            .toString());
        user = new User();
        return user;
    }
    
    public void addUser() {
        try {
            if (user != null) {
                if (!isValidPassword()) {
                    return;
                }
                if (!isRoleSelected()) {
                    return;
                }
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                user.setCreatedBy(currentUser);
                user.setCreateDate(new Date());
                Role selectedRole = manageSecurityRoleService.findRole(selectedRoleId);
                user.addRole(selectedRole);
                user = manageSecurityUserService.addUser(user);
                if (!JsfUtil.isValidationFailed()) {
                    users = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("UserAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The User was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateUser() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("UserBean: updateUser").toString());
        try {
            if (user != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                user.setLastModifiedBy(currentUser);
                user.setLastModifyDate(new Date());
                Role selectedRole = manageSecurityRoleService.findRole(selectedRoleId);
                user.getRoles().clear();
                user.addRole(selectedRole);
                user = manageSecurityUserService.updateUser(user);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("UserUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteUser() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("UserBean: deleteUser").toString());
        try {
            if (user != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                user.setLastModifiedBy(currentUser);
                user.setLastModifyDate(new Date());
                user = manageSecurityUserService.markUserDeleted(user);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("UserDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    user = null; // Remove selection
                    user = null;
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
    
    public void searchUsers() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("UserBean: searchUsers").toString());
        try {
            if (userSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                userSearchCriteria.setServiceUser(currentUser);
                users = manageSecurityUserService.searchUsers(userSearchCriteria);
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void ajaxCheckUserExists() {
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
        userSearchCriteria.setUsername(user.getUsername());
        userSearchCriteria.setServiceUser(currentUser);
        userSearchCriteria.setSearchType(SearchType.EQUALS);
        Collection<User> users = manageSecurityUserService.searchUsers(userSearchCriteria);
        if (users.size() > 0) {
            this.userExistsMessage = "Username already exists";
        }
        else {
            this.userExistsMessage = "Username unique";
        }
    }
    
    public void ajaxCheckEmailExists() {
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
        userSearchCriteria.setEmail(user.getEmail());
        userSearchCriteria.setServiceUser(currentUser);
        userSearchCriteria.setSearchType(SearchType.EQUALS);
        Collection<User> users = manageSecurityUserService.searchUsers(userSearchCriteria);
        if (users.size() > 0) {
            this.emailExistsMessage = "Email already exists";
        }
        else {
            this.emailExistsMessage = "Email unique";
        }
    }
    
    public Collection<Role> getRoles() {
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        RoleSearchCriteria roleSearchCriteria = new RoleSearchCriteria();
        roleSearchCriteria.setDeleted(false);
        roleSearchCriteria.setServiceUser(currentUser);
        return manageSecurityRoleService.searchRoles(roleSearchCriteria);
    }
    
    private boolean isValidPassword() {
        if (!user.getPassword().equals(getConfirmPassword())){
            String msg = "Confirmation Password does not match Password";
            JsfUtil.addErrorMessage(msg);
            return false;
        }
        return true;
    }
    
    private boolean isRoleSelected() {
        LogManager.getRootLogger().info(new StringBuilder()
                .append("Selected role: ")
                .append(Long.toString(selectedRoleId))
                .toString());
        if (selectedRoleId == null) {
            String msg = "A role must be selected";
            JsfUtil.addErrorMessage(msg);
            return false;
        }
        return true;
    }
    
}
