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
        return "/jericho/index.xhtml";
    }
    
    /* Security Menu */
    public String users() {
        return "/jericho/security/user/list-users.xhtml";
    }
    
    public String roles() {
        return "/jericho/security/role/list-roles.xhtml";
    }
    
    public String permissions() {
        return "/jericho/security/permission/list-permissions.xhtml";
    }
    
    public String auditActivities() {
        return "/jericho/auditactivity/list-audit-activity.xhtml";
    } 
    
    /* Property Menu */
    /* Properties Menu */
    public String properties() {
        return "/jericho/property/list-properties.xhtml";
    }
    
    /* Area Menu */
    public String areas() {
        return "/jericho/address/list-areas.xhtml";
    }
    
    /* Greater Area Menu */
    public String greaterAreas() {
        return "/jericho/address/list-greater-areas.xhtml";
    }
    
    /* Suburb Menu */
    public String suburbs() {
        return "/jericho/address/list-suburbs.xhtml";
    }
    
    /* Third Parties Menu */
    /* Attorney Menu */
    public String attorneys() {
        return "/jericho/attorney/list-attorneys.xhtml";
    }
    
    /* Bank Menu */
    public String banks() {
        return "/jericho/bank/list-banks.xhtml";
    }
    
    /* Contractor Menu */
    public String contractors() {
        return "/jericho/contractor/list-contractors.xhtml";
    }
    
    /* Estate Agent Menu */
    public String estateagents() {
        return "/jericho/estateagent/list-estate-agents.xhtml";
    }

    /* Logout */
    public String logout() {
        String destination = "/login.xhtml?faces-redirect=true";
        
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
            destination = "/loginerror.xhtml?faces-redirect=true";
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
