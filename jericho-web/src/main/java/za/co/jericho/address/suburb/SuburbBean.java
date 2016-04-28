package za.co.jericho.address.suburb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.LogManager;
import za.co.jericho.address.domain.Area;
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
    private Collection<Area> areas = new ArrayList<>();
    private Area selectedArea;
    private Long selectedAreaId;
    @EJB
    private ManageAddressService manageAddressService;
    
    public SuburbBean() {
        
    }
    
    @PostConstruct
    public void init() {
        LogManager.getRootLogger().info("SuburbBean: init: Loading all areas");
        /* Find all areas for association with a Suburb */
        areas = manageAddressService.findAllAreas();
        LogManager.getRootLogger().info("SuburbBean: init: areas size: " + areas.size());
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

    public Collection<Area> getAreas() {
        return areas;
    }

    public void setAreas(Collection<Area> areas) {
        this.areas = areas;
    }

    public Area getSelectedArea() {
        return selectedArea;
    }

    public void setSelectedArea(Area selectedArea) {
        this.selectedArea = selectedArea;
    }

    public Long getSelectedAreaId() {
        return selectedAreaId;
    }

    public void setSelectedAreaId(Long selectedAreaId) {
        this.selectedAreaId = selectedAreaId;
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
    
    public Collection<Area> getAreaList() {
        LogManager.getRootLogger().info("SuburbBean: getAreas()");
        return manageAddressService.findAllAreas();
    }
    
}
