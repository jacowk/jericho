package za.co.jericho.attorney;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.LogManager;
import za.co.jericho.attorney.domain.Attorney;
import za.co.jericho.attorney.search.AttorneySearchCriteria;
import za.co.jericho.attorney.service.ManageAttorneyService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-19
 */
@ManagedBean(name = "attorneyBean")
@SessionScoped
public class AttorneyBean implements Serializable {
    
    private Attorney attorney;
    private AttorneySearchCriteria attorneySearchCriteria = new AttorneySearchCriteria();
    private Collection<Attorney> attorneys = null;
    @EJB
    private ManageAttorneyService manageAttorneyService;
    
    public AttorneyBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public Attorney getAttorney() {
        return attorney;
    }

    public void setAttorney(Attorney attorney) {
        this.attorney = attorney;
    }

    public AttorneySearchCriteria getAttorneySearchCriteria() {
        return attorneySearchCriteria;
    }

    public void setAttorneySearchCriteria(AttorneySearchCriteria attorneySearchCriteria) {
        this.attorneySearchCriteria = attorneySearchCriteria;
    }

    public Collection<Attorney> getAttorneys() {
        return attorneys;
    }

    public void setAttorneys(Collection<Attorney> attorneys) {
        this.attorneys = attorneys;
    }

    public ManageAttorneyService getManageAttorneyService() {
        return manageAttorneyService;
    }

    public void setManageAttorneyService(ManageAttorneyService manageAttorneyService) {
        this.manageAttorneyService = manageAttorneyService;
    }
    
    /* Service calls */
    public Attorney prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AttorneyBean: prepareAdd")
            .toString());
        attorney = new Attorney();
        return attorney;
    }
    
    public void addAttorney() {
        try {
            if (attorney != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                attorney.setCreatedBy(currentUser);
                attorney.setCreateDate(new Date());
                attorney = manageAttorneyService.addAttorney(attorney);
                if (!JsfUtil.isValidationFailed()) {
                    attorneys = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("AttorneyAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Attorney was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateAttorney() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AttorneyBean: updateAttorney").toString());
        try {
            if (attorney != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                attorney.setLastModifiedBy(currentUser);
                attorney.setLastModifyDate(new Date());
                attorney = manageAttorneyService.updateAttorney(attorney);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("AttorneyUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteAttorney() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AttorneyBean: deleteAttorney").toString());
        try {
            if (attorney != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                attorney.setLastModifiedBy(currentUser);
                attorney.setLastModifyDate(new Date());
                attorney = manageAttorneyService.markAttorneyDeleted(attorney);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("AttorneyDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    attorney = null; // Remove selection
                    attorneys = null;
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
    
    public void searchAttorneys() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AttorneyBean: searchAttorneys").toString());
        try {
            if (attorneySearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                attorneySearchCriteria.setServiceUser(currentUser);
                attorneys = manageAttorneyService.searchAttorneys(attorneySearchCriteria);
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
