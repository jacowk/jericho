package za.co.jericho.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import za.co.jericho.audit.domain.AuditActivity;
import za.co.jericho.audit.search.AuditActivitySearchCriteria;
import za.co.jericho.audit.service.ManageAuditActivityService;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.UserSearchCriteria;
import za.co.jericho.security.service.ManageSecurityUserService;
import za.co.jericho.session.SessionServices;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-08-15
 */
@ManagedBean(name = "auditActivityBean")
@SessionScoped
public class AuditActivityBean implements Serializable {

    @EJB
    private ManageSecurityUserService manageSecurityUserService;
    @EJB
    private ManageAuditActivityService manageAuditActivityService;
    private AuditActivitySearchCriteria auditActivitySearchCriteria = new AuditActivitySearchCriteria();
    private Long selectedUserId;
    private Collection<User> userList;
    private Collection<AuditActivity> auditActivities = new ArrayList<AuditActivity>();
    private AuditActivity selected;
    
    /**
     * Creates a new instance of AuditBean
     */
    public AuditActivityBean() {
        
    }
    
    @PostConstruct
    public void initialize() {
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
        userSearchCriteria.setServiceUser(currentUser);
        userList = manageSecurityUserService.findAllUsersNotDeleted(userSearchCriteria);
    }

    public AuditActivitySearchCriteria getAuditActivitySearchCriteria() {
        return auditActivitySearchCriteria;
    }

    public void setAuditActivitySearchCriteria(AuditActivitySearchCriteria auditActivitySearchCriteria) {
        this.auditActivitySearchCriteria = auditActivitySearchCriteria;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public Collection<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Collection<AuditActivity> getAuditActivities() {
        return auditActivities;
    }

    public void setAuditActivities(Collection<AuditActivity> auditActivities) {
        this.auditActivities = auditActivities;
    }

    public AuditActivity getSelected() {
        return selected;
    }

    public void setSelected(AuditActivity selected) {
        this.selected = selected;
    }

    public String searchAuditActivity() {
        Logger.getRootLogger().info(new StringBuilder("AuditActivityBean: searchAuditActivity").toString());
        User user = manageSecurityUserService.findUser(selectedUserId);
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        auditActivitySearchCriteria.setServiceUser(currentUser);
        auditActivitySearchCriteria.setUser(user);
        auditActivitySearchCriteria.setActivityFromDate(auditActivitySearchCriteria.getActivityFromDate());
        auditActivitySearchCriteria.setActivityToDate(auditActivitySearchCriteria.getActivityToDate());
        auditActivities = manageAuditActivityService.searchAuditActivities(auditActivitySearchCriteria);
        return "";
    }
    
}
