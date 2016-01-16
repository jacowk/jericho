package za.co.jericho.address.area;

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
import za.co.jericho.address.domain.Area;
import za.co.jericho.address.search.AreaSearchCriteria;
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
@ManagedBean(name = "areaBean")
@SessionScoped
public class AreaBean implements Serializable {
    
    private Area area;
    private AreaSearchCriteria areaSearchCriteria = new AreaSearchCriteria();
    private Collection<Area> areas = null;
    @EJB
    private ManageAddressService manageAddressService;
    
    public AreaBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    /* Getters and Setters */

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public AreaSearchCriteria getAreaSearchCriteria() {
        return areaSearchCriteria;
    }

    public void setAreaSearchCriteria(AreaSearchCriteria areaSearchCriteria) {
        this.areaSearchCriteria = areaSearchCriteria;
    }

    public Collection<Area> getAreas() {
        return areas;
    }

    public void setAreas(Collection<Area> areas) {
        this.areas = areas;
    }

    public ManageAddressService getManageAddressService() {
        return manageAddressService;
    }

    public void setManageAddressService(ManageAddressService manageAddressService) {
        this.manageAddressService = manageAddressService;
    }
    
    /* Service calls */
    public Area prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AreaBean: prepareAdd")
            .toString());
        area = new Area();
        return area;
    }
    
    public void addArea() {
        try {
            if (area != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                area.setCreatedBy(currentUser);
                area.setCreateDate(new Date());
                area = manageAddressService.addArea(area);
                if (!JsfUtil.isValidationFailed()) {
                    areas = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("AreaAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Area was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateArea() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AreaBean: updateArea").toString());
        try {
            if (area != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                area.setLastModifiedBy(currentUser);
                area.setLastModifyDate(new Date());
                area = manageAddressService.updateArea(area);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("AreaUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteArea() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AreaBean: deleteArea").toString());
        try {
            if (area != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                area.setLastModifiedBy(currentUser);
                area.setLastModifyDate(new Date());
                area = manageAddressService.markAreaDeleted(area);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("AreaDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    area = null; // Remove selection
                    area = null;
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
    
    public void searchAreas() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AreaBean: searchAreas").toString());
        try {
            if (areaSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                areaSearchCriteria.setServiceUser(currentUser);
                areas = manageAddressService.searchAreas(areaSearchCriteria);
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
