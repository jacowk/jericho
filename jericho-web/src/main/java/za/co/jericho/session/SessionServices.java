package za.co.jericho.session;

import java.util.Objects;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 */
public class SessionServices {
    
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
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        User user = (User) session.getAttribute(SessionVariables.CURRENT_USER.getValue());
        if (user == null) {
            throw new RuntimeException("User not found on session.");
        }
        return user;
    }
    
    public void removeUserFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        User user = (User) session.getAttribute(SessionVariables.CURRENT_USER.getValue());
        if (user != null) {
            session.removeAttribute(SessionVariables.CURRENT_USER.getValue());
        }
    }
    
    public void setEntityOnSession(AbstractEntity abstractEntity) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute(SessionVariables.ENTITY.getValue(), abstractEntity);
    }
    
    public AbstractEntity getEntityFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        return (AbstractEntity) session.getAttribute(SessionVariables.ENTITY.getValue());
    }
    
    public void removeEntityFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        if (session.getAttribute(SessionVariables.ENTITY.getValue()) != null) {
            session.removeAttribute(SessionVariables.ENTITY.getValue());
        }
    }
    
}
