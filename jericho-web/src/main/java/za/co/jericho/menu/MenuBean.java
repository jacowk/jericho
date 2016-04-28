package za.co.jericho.menu;

import java.io.Serializable;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.audit.service.ManageAuditActivityService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.PathConstants;

/**
 * I decided to use a MenuBean for navigation, because of the following drawbacks
 * of navigation-rules in faces-config.xml:
 * 1) Using the b:navBar as the menu, combined with outcomes defined in faces-config.xml
 *      results in the user having to do a login for every single link or button click.
 * 2) Because the menu is in a template, you need to define per outcome, a navigation-rule
 *      for every single page in the system, even with <b:listLinks> for menus.
 * 
 * @author Jaco Koekemoer
 */
@ManagedBean(name = "menuBean")
@RequestScoped
public class MenuBean implements Serializable{
    
    @EJB
    private ManageAuditActivityService manageAuditActivityService;
    
    /**
     * Creates a new instance of LoginBean
     * <h:commandLink action="#{logoutBean.logout}" value="Logout" />
     */
    public MenuBean() {
        
    }
    
    /* Home Menu */
    public String home() {
        return PathConstants.HOME.getValue();
    }
    
    /* Security Menu */
    public String users() {
        return PathConstants.LIST_USERS.getValue();
    }
    
    public String roles() {
        return PathConstants.LIST_ROLES.getValue();
    }
    
    public String permissions() {
        return PathConstants.LIST_PERMISSIONS.getValue();
    }
    
    public String auditActivities() {
        return PathConstants.LIST_AUDIT_ACTIVITY.getValue();
    } 
    
    /* Property Menu */
    /* Properties Menu */
    public String properties() {
        return PathConstants.LIST_PROPERTIES.getValue();
    }
    
    /* Area Menu */
    public String areas() {
        return PathConstants.LIST_AREAS.getValue();
    }
    
    /* Greater Area Menu */
    public String greaterAreas() {
        return PathConstants.LIST_GREATER_AREAS.getValue();
    }
    
    /* Suburb Menu */
    public String suburbs() {
        return PathConstants.LIST_SUBBURBS.getValue();
    }
    
    /* Third Parties Menu */
    /* Attorney Menu */
    public String attorneys() {
        return PathConstants.LIST_ATTORNEYS.getValue();
    }
    
    /* Bank Menu */
    public String banks() {
        return PathConstants.LIST_BANKS.getValue();
    }
    
    /* Contractor Menu */
    public String contractors() {
        return PathConstants.LIST_CONTRACTORS.getValue();
    }
    
    /* Estate Agent Menu */
    public String estateagents() {
        return PathConstants.LIST_ESTATE_AGENTS.getValue();
    }
    
    /* Contacts Menu */
    public String contacts() {
        return PathConstants.LIST_CONTACTS.getValue();
    }

    /* Logout */
    public String logout() {
        StringBuilder destinationBuilder = new StringBuilder();
        destinationBuilder.append(PathConstants.LOGIN.getValue());
        destinationBuilder.append(PathConstants.FACES_REDIRECT.getValue());
        String destination = destinationBuilder.toString();
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        SessionServices sessionServices = new SessionServices();
//        sessionServices.removeUserFromSession();
        try {
//            logAuditActivity(request);
            request.logout();
        }
        catch (ServletException ex) {
            LogManager.getRootLogger().error(new StringBuilder()
                .append(ex)
                .toString());
            StringBuilder destinationErrorBuilder = new StringBuilder();
            destinationBuilder.append(PathConstants.LOGIN_ERROR.getValue());
            destinationBuilder.append(PathConstants.FACES_REDIRECT.getValue());
            destination = destinationErrorBuilder.toString();
        }
        return destination;
    }
    
    private void logAuditActivity(HttpServletRequest request) {
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.SECURITY_USER, //String entityName
            "", //String serviceName
            AuditActivityType.LOGOUT, //String activityType
            "User logged out", //String description
            currentUser)); //User currentUser
    }
    
}
