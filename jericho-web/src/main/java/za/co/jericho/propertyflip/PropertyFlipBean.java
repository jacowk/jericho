package za.co.jericho.propertyflip;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.LogManager;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.propertyflip.search.PropertyFlipSearchCriteria;
import za.co.jericho.propertyflip.service.ManagePropertyFlipService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-13
 */
@ManagedBean(name = "propertyFlipBean")
@SessionScoped
public class PropertyFlipBean implements Serializable {
    
    private PropertyFlip propertyFlip;
    private PropertyFlipSearchCriteria propertyFlipSearchCriteria = new PropertyFlipSearchCriteria();
    private Collection<PropertyFlip> propertyFlips = null;
    @EJB
    private ManagePropertyFlipService managePropertyFlipService;
    
    public PropertyFlipBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    public PropertyFlipSearchCriteria getPropertyFlipSearchCriteria() {
        return propertyFlipSearchCriteria;
    }

    public void setPropertyFlipSearchCriteria(PropertyFlipSearchCriteria propertyFlipSearchCriteria) {
        this.propertyFlipSearchCriteria = propertyFlipSearchCriteria;
    }

    public Collection<PropertyFlip> getPropertyFlips() {
        return propertyFlips;
    }

    public void setPropertyFlips(Collection<PropertyFlip> propertyFlips) {
        this.propertyFlips = propertyFlips;
    }

    public ManagePropertyFlipService getManagePropertyFlipService() {
        return managePropertyFlipService;
    }

    public void setManagePropertyFlipService(ManagePropertyFlipService managePropertyFlipService) {
        this.managePropertyFlipService = managePropertyFlipService;
    }
    
    /* Service calls */
    public PropertyFlip prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyFlipBean: prepareAdd")
            .toString());
        propertyFlip = new PropertyFlip();
        return propertyFlip;
    }
    
    public void addPropertyFlip() {
        try {
            if (propertyFlip != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                propertyFlip.setCreatedBy(currentUser);
                propertyFlip.setCreateDate(new Date());
                propertyFlip = managePropertyFlipService.addPropertyFlip(propertyFlip);
                if (!JsfUtil.isValidationFailed()) {
                    propertyFlips = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("PropertyFlipAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The PropertyFlip was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updatePropertyFlip() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyFlipBean: updatePropertyFlip").toString());
        try {
            if (propertyFlip != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                propertyFlip.setLastModifiedBy(currentUser);
                propertyFlip.setLastModifyDate(new Date());
                propertyFlip = managePropertyFlipService.updatePropertyFlip(propertyFlip);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("PropertyFlipUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deletePropertyFlip() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyFlipBean: deletePropertyFlip").toString());
        try {
            if (propertyFlip != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                propertyFlip.setLastModifiedBy(currentUser);
                propertyFlip.setLastModifyDate(new Date());
                propertyFlip = managePropertyFlipService.markPropertyFlipDeleted(propertyFlip);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("PropertyFlipDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    propertyFlip = null; // Remove selection
                    propertyFlip = null;
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
    
    public void searchPropertyFlips() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyFlipBean: searchPropertyFlips").toString());
        try {
            if (propertyFlipSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                propertyFlipSearchCriteria.setServiceUser(currentUser);
                propertyFlips = managePropertyFlipService.searchPropertyFlips(propertyFlipSearchCriteria);
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
