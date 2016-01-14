package za.co.jericho.security.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.LogManager;
import za.co.jericho.common.search.SearchType;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.RoleSearchCriteria;
import za.co.jericho.security.search.UserSearchCriteria;
import za.co.jericho.security.service.ManageSecurityRoleService;
import za.co.jericho.security.service.ManageSecurityUserService;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable{

    private UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
    private Collection<User> users = null;
    private User selected;
    private Long selectedRoleId; /* Need this for combobox selections */
    private Role selectedRole; /* This is purely for display on the view screen */
    private String confirmPassword = "";
    private String userExistsMessage = "";
    private String emailExistsMessage = "";
    @EJB
    private ManageSecurityUserService manageSecurityUserService;
    @EJB
    private ManageSecurityRoleService manageSecurityRoleService;

    public UserBean() {
    }

    public UserSearchCriteria getUserSearchCriteria() {
        return userSearchCriteria;
    }
    
    @PostConstruct
    public void initialize() {
        /* This code is to ensure that the role is preselected on screen in the combobox */
        if (selected != null && selected.getRoles() != null && selected.getRoles().size() > 0) {
            Role role = (Role) selected.getRoles().iterator().next();
            if (role != null) {
                setSelectedRoleId(role.getId());
                setSelectedRole(role);
            }
        }
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

    public User getSelected() {
        //TODO: Only one role supported for now from the front end
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
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

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public User prepareCreate() {
        selected = new User();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/TestBundle").getString("UserCreated"));
        if (!JsfUtil.isValidationFailed()) {
            users = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/TestBundle").getString("UserUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/TestBundle").getString("UserDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            users = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == JsfUtil.PersistAction.CREATE) {
                    saveUser();
                }
                else if (persistAction == JsfUtil.PersistAction.UPDATE) {
                    updateUser();
                }
                else {
                    throw new DeleteNotSupportedException("Deleting a user is not supported");
                }
                JsfUtil.addSuccessMessage(successMessage);
            }
            catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/TestBundle").getString("PersistenceErrorOccured"));
                }
            }
            catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/TestBundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public User getUser(java.lang.Long id) {
        return (User) manageSecurityUserService.findUser(id);
    }

    public Collection<User> getItemsAvailableSelectMany() {
        return manageSecurityUserService.findAllUsers();
    }

    public Collection<User> getItemsAvailableSelectOne() {
        return manageSecurityUserService.findAllUsers();
    }
    
    public Collection<Role> getRoles() {
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        RoleSearchCriteria roleSearchCriteria = new RoleSearchCriteria();
        roleSearchCriteria.setDeleted(false);
        roleSearchCriteria.setServiceUser(currentUser);
        return manageSecurityRoleService.searchRoles(roleSearchCriteria);
    }
    
    public String searchUsers() {
        LogManager.getRootLogger().info(new StringBuilder()
                .append("UserBean: searchUsers")
                .toString());
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        userSearchCriteria.setServiceUser(currentUser);
        users = manageSecurityUserService.searchUsers(userSearchCriteria);
        return null;
    }
    
    public String saveUser() throws Exception {
        LogManager.getRootLogger().info(new StringBuilder()
                .append("UserBean: saveUser")
                .toString());
        //Validate the email address, perhaps client side with a validator component
        if (!isValidPassword()) {
            return null;
        }
        if (!isRoleSelected()) {
            return null;
        }
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        selected.setCreatedBy(currentUser);
        selected.setCreateDate(new Date());
        Role selectedRole = manageSecurityRoleService.findRole(selectedRoleId);
        selected.getRoles().add(selectedRole);
        selected = manageSecurityUserService.addUser(selected);
        return "";
    }
    
    public String updateUser() throws Exception {
        LogManager.getRootLogger().info(new StringBuilder()
                .append("UserBean: updateUser")
                .toString());
        //Validate the email address, perhaps client side with a validator component
        if (!isRoleSelected()) {
            return null;
        }
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        selected.setLastModifiedBy(currentUser);
        selected.setLastModifyDate(new Date());
        Role selectedRole = manageSecurityRoleService.findRole(selectedRoleId);
        selected.getRoles().clear();
        selected.getRoles().add(selectedRole);
        selected = manageSecurityUserService.updateUser(selected);
        return "";
    }
    
    private boolean isValidPassword() {
        if (!selected.getPassword().equals(getConfirmPassword())){
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
    
    public void ajaxCheckUserExists() {
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
        userSearchCriteria.setUsername(selected.getUsername());
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
        userSearchCriteria.setEmail(selected.getEmail());
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

    @FacesConverter(forClass = User.class)
    public static class UserBeanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserBean bean = (UserBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userBean");
            return bean.getUser(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getId());
            }
            else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), User.class.getName()});
                return null;
            }
        }

    }
    
}
