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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.LogManager;
import za.co.jericho.address.domain.GreaterArea;
import za.co.jericho.address.search.GreaterAreaSearchCriteria;
import za.co.jericho.address.service.ManageAddressService;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.property.domain.Property;
import za.co.jericho.property.search.PropertySearchCriteria;
import za.co.jericho.property.service.ManagePropertyService;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-21
 */
@ManagedBean(name = "propertyBeanOld")
@SessionScoped
public class PropertyBeanOld implements Serializable {
    
    private PropertySearchCriteria propertySearchCriteria = new PropertySearchCriteria();
    private Collection<Property> properties = null;
    private Property property;
    private PropertyFlip propertyFlip;
    private Long selectedGreaterAreaId;
    private GreaterArea selectedGreaterArea;
    @EJB
    private ManagePropertyService managePropertyService;
    @EJB
    private ManageAddressService manageAddressService;
    
    public PropertyBeanOld(){
        
    }
    
    @PostConstruct
    public void initialize() {
        /* This code is to ensure that the greater area is preselected on screen in the combobox */
        if (property != null && property.getAddress() != null && property.getAddress().getGreaterArea() != null) {
            GreaterArea greaterArea = (GreaterArea) property.getAddress().getGreaterArea();
            setSelectedGreaterAreaId(greaterArea.getId());
            setSelectedGreaterArea(greaterArea);
        }
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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property selected) {
        this.property = selected;
    }

    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {
        this.propertyFlip = propertyFlip;
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
    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }
    
    public Property prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBeanBean: prepareAdd")
            .toString());
        property = new Property();
        propertyFlip = new PropertyFlip();
        initializeEmbeddableKey();
        return property;
    }
    
    public void create() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBeanBean: create")
            .toString());
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/JerichoBundle").getString("PropertyCreated"));
        if (!JsfUtil.isValidationFailed()) {
            properties = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBeanBean: update")
            .toString());
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/JerichoBundle").getString("UserUpdated"));
    }

    public void destroy() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBeanBean: destroy")
            .toString());
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/JerichoBundle").getString("UserDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            property = null; // Remove selection
            properties = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBeanBean: persist")
            .toString());
        if (property != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == JsfUtil.PersistAction.CREATE) {
                    saveProperty();
                }
                else if (persistAction == JsfUtil.PersistAction.UPDATE) {
                    updateProperty();
                }
                else {
                    throw new DeleteNotSupportedException("Deleting a property is not supported");
                }
                JsfUtil.addSuccessMessage(successMessage);
            }
            catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/JerichoBundle").getString("PersistenceErrorOccured"));
                }
            }
            catch (Exception ex) {
                LogManager.getRootLogger().error(new StringBuilder()
                    .append(ex)
                    .toString());
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/JerichoBundle").getString("PersistenceErrorOccured"));
            }
        }
    }
    
    public Property getProperty(java.lang.Long id) {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBeanBean: getProperty")
            .toString());
        return managePropertyService.findProperty(id);
    }
    
    public Collection<Property> getItemsAvailableSelectMany() {
        return managePropertyService.findAllProperties();
    }

    public Collection<Property> getItemsAvailableSelectOne() {
        return managePropertyService.findAllProperties();
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
    
    public String searchProperties() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBean: searchProperties")
            .toString());
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        propertySearchCriteria.setServiceUser(currentUser);
        properties = managePropertyService.searchProperties(propertySearchCriteria);
        return null;
    }
    
    public String saveProperty() throws Exception {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBeanBean: saveProperty")
            .toString());
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        
        property.setCreatedBy(currentUser);
        property.setCreateDate(new Date());
//        GreaterArea selectedGreaterArea = null;
        if (selectedGreaterAreaId != null && selectedGreaterAreaId > 0) {
            selectedGreaterArea = manageAddressService.findGreaterArea(selectedGreaterAreaId);
        }
        if (property.getAddress() != null) {
            property.getAddress().setCreatedBy(currentUser);
            property.getAddress().setCreateDate(new Date());
            property.getAddress().setGreaterArea(selectedGreaterArea);
        }
        property = managePropertyService.addProperty(property);
        return "";
    }
    
    public String updateProperty() throws Exception {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBeanBean: updateProperty")
            .toString());
        SessionServices sessionServices = new SessionServices();
        User currentUser = sessionServices.getUserFromSession();
        property.setLastModifiedBy(currentUser);
        property.setLastModifyDate(new Date());
//        GreaterArea selectedGreaterArea = null;
        if (selectedGreaterAreaId != null && selectedGreaterAreaId > 0) {
            selectedGreaterArea = manageAddressService.findGreaterArea(selectedGreaterAreaId);
        }
        if (property.getAddress() != null) {
            property.getAddress().setLastModifiedBy(currentUser);
            property.getAddress().setLastModifyDate(new Date());
            property.getAddress().setGreaterArea(selectedGreaterArea);
        }
        property = managePropertyService.updateProperty(property);
        return "";
    }

    @FacesConverter(forClass = Property.class)
    public static class PropertyBeanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PropertyBeanOld bean = (PropertyBeanOld) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "propertyBean");
            return bean.getProperty(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Property) {
                Property o = (Property) object;
                return getStringKey(o.getId());
            }
            else {
                LogManager.getRootLogger().info(new StringBuilder()
                    .append("Object not of type Property")
                    .toString());
                return null;
            }
        }

    }
    
    public String managePropertyFlip() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyBean: managePropertyFlip")
            .toString());
        return "/jericho/propertyflip/manage-property-flip.xhtml?faces-redirect=true";
    }
    
}
