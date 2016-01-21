package za.co.jericho.address.suburb;

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
import za.co.jericho.address.domain.Suburb;
import za.co.jericho.address.search.SuburbSearchCriteria;
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
@ManagedBean(name = "suburbBean")
@SessionScoped
public class SuburbBean implements Serializable {
    
    private Suburb suburb;
    private SuburbSearchCriteria suburbSearchCriteria = new SuburbSearchCriteria();
    private Collection<Suburb> suburbs = null;
    @EJB
    private ManageAddressService manageAddressService;
    
    public SuburbBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public Suburb getSuburb() {
        return suburb;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }

    public SuburbSearchCriteria getSuburbSearchCriteria() {
        return suburbSearchCriteria;
    }

    public void setSuburbSearchCriteria(SuburbSearchCriteria suburbSearchCriteria) {
        this.suburbSearchCriteria = suburbSearchCriteria;
    }

    public Collection<Suburb> getSuburbs() {
        return suburbs;
    }

    public void setSuburbs(Collection<Suburb> suburbs) {
        this.suburbs = suburbs;
    }

    public ManageAddressService getManageAddressService() {
        return manageAddressService;
    }

    public void setManageAddressService(ManageAddressService manageAddressService) {
        this.manageAddressService = manageAddressService;
    }

    /* Service calls */
    public Suburb prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SuburbBean: prepareAdd")
            .toString());
        suburb = new Suburb();
        return suburb;
    }
    
    public void addSuburb() {
        try {
            if (suburb != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                suburb.setCreatedBy(currentUser);
                suburb.setCreateDate(new Date());
                suburb = manageAddressService.addSuburb(suburb);
                if (!JsfUtil.isValidationFailed()) {
                    suburbs = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("SuburbAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Suburb was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateSuburb() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SuburbBean: updateSuburb").toString());
        try {
            if (suburb != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                suburb.setLastModifiedBy(currentUser);
                suburb.setLastModifyDate(new Date());
                suburb = manageAddressService.updateSuburb(suburb);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("SuburbUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteSuburb() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SuburbBean: deleteSuburb").toString());
        try {
            if (suburb != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                suburb.setLastModifiedBy(currentUser);
                suburb.setLastModifyDate(new Date());
                suburb = manageAddressService.markSuburbDeleted(suburb);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("SuburbDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    suburb = null; // Remove selection
                    suburb = null;
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
    
    public void searchSuburbs() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SuburbBean: searchSuburbs").toString());
        try {
            if (suburbSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                suburbSearchCriteria.setServiceUser(currentUser);
                suburbs = manageAddressService.searchSuburbs(suburbSearchCriteria);
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
