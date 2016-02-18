package za.co.jericho.property;

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
import za.co.jericho.address.domain.GreaterArea;
import za.co.jericho.address.search.GreaterAreaSearchCriteria;
import za.co.jericho.address.service.ManageAddressService;
import za.co.jericho.property.domain.Property;
import za.co.jericho.property.search.PropertySearchCriteria;
import za.co.jericho.property.service.ManagePropertyService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-18
 */
@ManagedBean(name = "propertyBean")
@SessionScoped
public class PropertyBean implements Serializable {
    
    private Property property;
    private PropertySearchCriteria propertySearchCriteria = new PropertySearchCriteria();
    private Collection<Property> properties = null;
    private Long selectedGreaterAreaId;
    private GreaterArea selectedGreaterArea;
    @EJB
    private ManagePropertyService managePropertyService;
    @EJB
    private ManageAddressService manageAddressService;
    
    public PropertyBean() {
        
    }
    
    @PostConstruct
    public void init() {
        /* This code is to ensure that the greater area is preselected on screen in the combobox */
        if (property != null && property.getAddress() != null && property.getAddress().getGreaterArea() != null) {
            GreaterArea greaterArea = (GreaterArea) property.getAddress().getGreaterArea();
            setSelectedGreaterAreaId(greaterArea.getId());
            setSelectedGreaterArea(greaterArea);
        }
    }

    /* Getters and Setters */
    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public PropertySearchCriteria getPropertySearchCriteria() {
        return propertySearchCriteria;
    }

    public void setPropertySearchCriteria(PropertySearchCriteria propertySearchCriteria) {
        this.propertySearchCriteria = propertySearchCriteria;
    }

    public Collection<Property> getProperties() {
        return properties;
    }

    public void setProperties(Collection<Property> properties) {
        this.properties = properties;
    }

    public Long getSelectedGreaterAreaId() {
        return selectedGreaterAreaId;
    }

    public void setSelectedGreaterAreaId(Long selectedGreaterAreaId) {
        this.selectedGreaterAreaId = selectedGreaterAreaId;
    }

    public GreaterArea getSelectedGreaterArea() {
        return selectedGreaterArea;
    }

    public void setSelectedGreaterArea(GreaterArea selectedGreaterArea) {
        this.selectedGreaterArea = selectedGreaterArea;
    }

    public ManagePropertyService getManagePropertyService() {
        return managePropertyService;
    }

    public void setManagePropertyService(ManagePropertyService managePropertyService) {
        this.managePropertyService = managePropertyService;
    }
    
    /* Service calls */
    public Property prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBean: prepareAdd")
            .toString());
        property = new Property();
        return property;
    }
    
    public void addProperty() {
        try {
            if (property != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                property.setCreatedBy(currentUser);
                property.setCreateDate(new Date());
                if (selectedGreaterAreaId != null && selectedGreaterAreaId > 0) {
                    selectedGreaterArea = manageAddressService.findGreaterArea(selectedGreaterAreaId);
                }
                if (property.getAddress() != null) {
                    property.getAddress().setCreatedBy(currentUser);
                    property.getAddress().setCreateDate(new Date());
                    property.getAddress().setGreaterArea(selectedGreaterArea);
                }
                if (property.getPropertyFlip() != null) {
                    property.getPropertyFlip().setCreatedBy(currentUser);
                    property.getPropertyFlip().setCreateDate(new Date());
                }
                property = managePropertyService.addProperty(property);
                if (!JsfUtil.isValidationFailed()) {
                    properties = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("PropertyAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Property was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateProperty() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBean: updateProperty").toString());
        try {
            if (property != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                property.setLastModifiedBy(currentUser);
                property.setLastModifyDate(new Date());
                if (selectedGreaterAreaId != null && selectedGreaterAreaId > 0) {
                    selectedGreaterArea = manageAddressService.findGreaterArea(selectedGreaterAreaId);
                }
                if (property.getAddress() != null) {
                    property.getAddress().setLastModifiedBy(currentUser);
                    property.getAddress().setLastModifyDate(new Date());
                    property.getAddress().setGreaterArea(selectedGreaterArea);
                }
                if (property.getPropertyFlip() != null) {
                    property.getPropertyFlip().setLastModifiedBy(currentUser);
                    property.getPropertyFlip().setLastModifyDate(new Date());
                }
                property = managePropertyService.updateProperty(property);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("PropertyUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteProperty() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBean: deleteProperty").toString());
        try {
            if (property != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                property.setLastModifiedBy(currentUser);
                property.setLastModifyDate(new Date());
                property = managePropertyService.markPropertyDeleted(property);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("PropertyDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    property = null; // Remove selection
                    property = null;
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
    
    public void searchProperties() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBean: searchPropertys").toString());
        try {
            if (propertySearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                propertySearchCriteria.setServiceUser(currentUser);
                properties = managePropertyService.searchProperties(propertySearchCriteria);
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public Collection<GreaterArea> getGreaterAreas() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBean: getGreaterAreas")
            .toString());
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        GreaterAreaSearchCriteria greaterAreaSearchCriteria = new GreaterAreaSearchCriteria();
        greaterAreaSearchCriteria.setName(""); /* Find all greater areas */
        greaterAreaSearchCriteria.setDeleted(false);
        greaterAreaSearchCriteria.setServiceUser(currentUser);
        /* Find all the greater areas */
        return manageAddressService.searchGreaterAreas(greaterAreaSearchCriteria);
    }
    
}
