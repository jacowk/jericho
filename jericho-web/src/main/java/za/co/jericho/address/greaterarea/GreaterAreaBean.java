package za.co.jericho.address.greaterarea;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import org.apache.log4j.LogManager;
import za.co.jericho.address.domain.GreaterArea;
import za.co.jericho.address.search.GreaterAreaSearchCriteria;
import za.co.jericho.address.service.ManageAddressService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-16
 */
public class GreaterAreaBean implements Serializable {
    
    private GreaterArea greaterArea;
    private GreaterAreaSearchCriteria greaterAreaSearchCriteria = new GreaterAreaSearchCriteria();
    private Collection<GreaterArea> greaterAreas = null;
    @EJB
    private ManageAddressService manageAddressService;
    
    public GreaterAreaBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public GreaterArea getGreaterArea() {
        return greaterArea;
    }

    public void setGreaterArea(GreaterArea greaterArea) {
        this.greaterArea = greaterArea;
    }

    public GreaterAreaSearchCriteria getGreaterAreaSearchCriteria() {
        return greaterAreaSearchCriteria;
    }

    public void setGreaterAreaSearchCriteria(GreaterAreaSearchCriteria greaterAreaSearchCriteria) {
        this.greaterAreaSearchCriteria = greaterAreaSearchCriteria;
    }

    public Collection<GreaterArea> getGreaterAreas() {
        return greaterAreas;
    }

    public void setGreaterAreas(Collection<GreaterArea> greaterAreas) {
        this.greaterAreas = greaterAreas;
    }

    public ManageAddressService getManageAddressService() {
        return manageAddressService;
    }

    public void setManageAddressService(ManageAddressService manageAddressService) {
        this.manageAddressService = manageAddressService;
    }
    
    /* Service calls */
    public GreaterArea prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("GreaterAreaBean: prepareAdd")
            .toString());
        greaterArea = new GreaterArea();
        return greaterArea;
    }
    
    public void addGreaterArea() {
        try {
            if (greaterArea != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                greaterArea.setCreatedBy(currentUser);
                greaterArea.setCreateDate(new Date());
                greaterArea = manageAddressService.addGreaterArea(greaterArea);
                if (!JsfUtil.isValidationFailed()) {
                    greaterAreas = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("GreaterAreaAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The GreaterArea was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateGreaterArea() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("GreaterAreaBean: updateGreaterArea").toString());
        try {
            if (greaterArea != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                greaterArea.setLastModifiedBy(currentUser);
                greaterArea.setLastModifyDate(new Date());
                greaterArea = manageAddressService.updateGreaterArea(greaterArea);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("GreaterAreaUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteGreaterArea() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("GreaterAreaBean: deleteGreaterArea").toString());
        try {
            if (greaterArea != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                greaterArea.setLastModifiedBy(currentUser);
                greaterArea.setLastModifyDate(new Date());
                greaterArea = manageAddressService.markGreaterAreaDeleted(greaterArea);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("GreaterAreaDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    greaterArea = null; // Remove selection
                    greaterArea = null;
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
    
    public void searchGreaterAreas() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("GreaterAreaBean: searchGreaterAreas").toString());
        try {
            if (greaterAreaSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                greaterAreaSearchCriteria.setServiceUser(currentUser);
                greaterAreas = manageAddressService.searchGreaterAreas(greaterAreaSearchCriteria);
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
