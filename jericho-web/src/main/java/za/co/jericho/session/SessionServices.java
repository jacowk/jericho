package za.co.jericho.session;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.security.domain.User;
import za.co.jericho.util.PathConstants;

/**
 * This class performs session related services, and is handled as Singleton.
 * 
 * @author Jaco Koekemoer
 */
public class SessionServices {
    
    private static SessionServices sessionServices;
    
    /* Retrieve singleton */
    public static SessionServices getInstance() {
        if (sessionServices == null) {
            sessionServices = new SessionServices();
        }
        return sessionServices;
    }
    
    /* Manage a user on the session */
    public void setUserOnSession(HttpServletRequest httpServletRequest, User user) {
        LogManager.getRootLogger().info("SessionServices: setUserOnSession(httpServletRequest, user)");
        HttpSession session = httpServletRequest.getSession();
        setUserOnSession(session, user);
    }
    
    public void setUserOnSession(User user) {
        LogManager.getRootLogger().info("SessionServices: setUserOnSession(user)");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        setUserOnSession(session, user);
    }
    
    private void setUserOnSession(HttpSession session, User user) {
        LogManager.getRootLogger().info("SessionServices: setUserOnSession(session, user)");
        if (session.getAttribute(SessionVariables.CURRENT_USER.getValue()) == null) {
            session.setAttribute(SessionVariables.CURRENT_USER.getValue(), user);
        }
        else {
            User userOnSession = (User) session.getAttribute(
                SessionVariables.CURRENT_USER.getValue());
            if (!Objects.equals(userOnSession.getId(), user.getId())) {
                session.removeAttribute(SessionVariables.CURRENT_USER.getValue());
                session.setAttribute(SessionVariables.CURRENT_USER.getValue(), user);
            }
        }
    }
    
    public User getUserFromSession() {
        LogManager.getRootLogger().info("SessionServices: getUserFromSession");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        User user = (User) session.getAttribute(SessionVariables.CURRENT_USER.getValue());
        if (user == null) {
            LogManager.getRootLogger().error("SessionServices: User not found on session.");
            session.invalidate();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            try {
                response.sendRedirect(PathConstants.LOGIN.getValue());
            }
            catch (IOException ex) {
                Logger.getLogger(SessionServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }
    
    public void removeUserFromSession() {
        LogManager.getRootLogger().info("SessionServices: removeUserFromSession");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        User user = (User) session.getAttribute(SessionVariables.CURRENT_USER.getValue());
        if (user != null) {
            session.removeAttribute(SessionVariables.CURRENT_USER.getValue());
        }
    }
    
    /* Manage an entity on the session */
    public void setEntityOnSession(AbstractEntity abstractEntity) {
        LogManager.getRootLogger().info("SessionServices: setEntityOnSession");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute(SessionVariables.ENTITY.getValue(), abstractEntity);
    }
    
    public AbstractEntity getEntityFromSession() {
        LogManager.getRootLogger().info("SessionServices: getEntityFromSession");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return (AbstractEntity) session.getAttribute(SessionVariables.ENTITY.getValue());
    }
    
    public void removeEntityFromSession() {
        LogManager.getRootLogger().info("SessionServices: removeEntityFromSession");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session.getAttribute(SessionVariables.ENTITY.getValue()) != null) {
            session.removeAttribute(SessionVariables.ENTITY.getValue());
        }
    }
    
    /* Manage an object on the session */
    public void setObjectOnSession(Object object, String sessionVariable) {
        LogManager.getRootLogger().info("SessionServices: setObjectOnSession: " + sessionVariable);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        if (session.getAttribute(sessionVariable) != null) {
            /* If the variable already exist on the session, remove it first */
            session.removeAttribute(sessionVariable);
            /* Now add the new variable to the session */
            session.setAttribute(sessionVariable, object);
        }
        else {
            /* If the variable does not exist on the session, add it to the session */
            session.setAttribute(sessionVariable, object);
        }
    }
    
    /* Get an object from the session, and return it */
    public Object getObjectFromSession(String sessionVariable) {
        LogManager.getRootLogger().info("SessionServices: getObjectFromSession: " + sessionVariable);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session.getAttribute(sessionVariable) != null) {
            return session.getAttribute(sessionVariable);
        }
        return null;
    }
    
    public void removeObjectFromSession(String sessionVariable) {
        LogManager.getRootLogger().info("SessionServices: removeObjectFromSession: " + sessionVariable);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session.getAttribute(sessionVariable) != null) {
            session.removeAttribute(sessionVariable);
        }
    }
    
}
