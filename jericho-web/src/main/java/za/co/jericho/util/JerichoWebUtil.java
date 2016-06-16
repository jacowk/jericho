package za.co.jericho.util;

import java.util.ResourceBundle;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.LogManager;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-19
 */
public class JerichoWebUtil {
    
    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }
    
    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }
    
    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    
    public static void addEJBExceptionMessage(EJBException ex) {
        String msg = "";
        Throwable cause = ex.getCause();
        if (cause != null) {
            msg = cause.getLocalizedMessage();
        }
        if (msg.length() > 0) {
            addErrorMessage(msg);
        }
        else {
            addErrorMessage(ex, ResourceBundle
                .getBundle("/JerichoWebBundle")
                .getString("PersistenceErrorOccured"));
        }
    }
    
    public static void addGeneralExceptionMessage(Exception e) {
        LogManager.getRootLogger().error(new StringBuilder()
            .append(e)
            .toString());
        addErrorMessage(e, ResourceBundle
            .getBundle("/JerichoWebBundle")
            .getString("PersistenceErrorOccured"));
    }
    
}
