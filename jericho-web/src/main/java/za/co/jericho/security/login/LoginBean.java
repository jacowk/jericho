package za.co.jericho.security.login;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;

/**
 *
 * @author Jaco Koekemoer
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean implements Serializable{
    
    private String username;
    private String password;
    
//    private User user;
//    @EJB
//    private ManageSecurityAuthenticationServiceBean service;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void login() {
//        if (service.login(user)) {
//            return "index";
//        }
        LogManager.getRootLogger().info("LoginBean: login");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try
        {
            request.login(username, password);
        }
        catch(ServletException e)
        {
//            JsfUtils.addErrorMessage(e, "authentication failed");
            LogManager.getRootLogger().error(new StringBuilder()
                .append(e)
                .toString());
            e.printStackTrace();
        }
    }
    
    public String logout() {
        LogManager.getRootLogger().info("LoginBean: logout");
        String destination = "/index?faces-redirect=true";
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.getSession().invalidate();
            request.logout();
        }
        catch (ServletException ex) {
            LogManager.getRootLogger().error(new StringBuilder()
                .append(ex)
                .toString());
            ex.printStackTrace();
            destination = "/loginerror?faces-redirect=true";
        }
        return destination;
    }
    
//    public User getUser() {
//        return user;
//    }
    
}
