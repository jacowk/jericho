package za.co.jericho.propertyflip;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.log4j.LogManager;
import za.co.jericho.property.PropertyBean;
import za.co.jericho.property.domain.Property;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.propertyflip.search.PropertyFlipSearchCriteria;
import za.co.jericho.propertyflip.service.ManagePropertyFlipService;
import za.co.jericho.security.domain.User;
import za.co.jericho.seller.SellerBean;
import za.co.jericho.session.SessionServices;
import za.co.jericho.session.SessionVariables;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;
import za.co.jericho.util.PathConstants;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-13
 */
@ManagedBean(name = "propertyFlipBean")
@SessionScoped
public class PropertyFlipBean implements Serializable {
    
    private Property property;
    private PropertyFlip propertyFlip;
    private PropertyFlipSearchCriteria propertyFlipSearchCriteria = new PropertyFlipSearchCriteria();
    private Collection<PropertyFlip> propertyFlips = null;
    @EJB
    private ManagePropertyFlipService managePropertyFlipService;
    @ManagedProperty(value = "#{propertyBean}")
    private PropertyBean propertyBean;
    private boolean createPropertyFlip; /* If false, then update */
    
    public PropertyFlipBean() {
        
    }
    
    @PostConstruct
    public void init() {
        LogManager.getRootLogger().info("PropertyFlipBean: init");
        property = propertyBean.getProperty();
        LogManager.getRootLogger().info("property == null: " + (property == null));
        if (property != null) {
            if (property.getPropertyFlip() == null) {
                propertyFlip = new PropertyFlip();
                createPropertyFlip = true;
                prepareDataForTesting(); //TODO Temporary step
            }
            else {
                propertyFlip = property.getPropertyFlip();
                createPropertyFlip = false;
            }
        }
    }

    private void prepareDataForTesting() {
        propertyFlip.setReferenceNumber(1234L);
        propertyFlip.setTitleDeedNumber("1234");
        propertyFlip.setCaseNumber("1234");
    }
    
    /* Getters and Setters */
    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

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

    public PropertyBean getPropertyBean() {
        return propertyBean;
    }

    public void setPropertyBean(PropertyBean propertyBean) {
        this.propertyBean = propertyBean;
    }

    public boolean isCreatePropertyFlip() {
        return createPropertyFlip;
    }

    public void setCreatePropertyFlip(boolean createPropertyFlip) {
        this.createPropertyFlip = createPropertyFlip;
    }
    
    /* Service calls */
    public void addPropertyFlip() {
        try {
            if (propertyFlip != null) {
                SessionServices sessionServices = SessionServices.getInstance();
                User currentUser = sessionServices.getUserFromSession();
                propertyFlip.setCreatedBy(currentUser);
                propertyFlip.setCreateDate(new Date());
                propertyFlip = managePropertyFlipService.addPropertyFlip(propertyFlip);
                if (!JsfUtil.isValidationFailed()) {
                    propertyFlips = null;
                }
                createPropertyFlip = false;
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
        navigateManagePropertyFlip();
    }
    
    public void updatePropertyFlip() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PropertyFlipBean: updatePropertyFlip").toString());
        try {
            if (propertyFlip != null) {
                SessionServices sessionServices = SessionServices.getInstance();
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
        navigateManagePropertyFlip();
    }
    
    /* Page Navigation */
    public void navigateManagePropertyFlip() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + 
                PathConstants.MANAGE_PROPERTY_FLIP_PATH.getValue());
        }
        catch (IOException ex) {
            Logger.getLogger(PropertyFlipBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void navigateUpdatePropertyFlip() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + 
                PathConstants.UPDATE_PROPERTY_FLIP_PATH.getValue());
        }
        catch (IOException ex) {
            Logger.getLogger(PropertyFlipBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Redirect page to the page for adding a seller
     */
    public void navigateAddSeller() {
        /* Now navigate to add-seller page */
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + 
                PathConstants.ADD_SELLER_PATH.getValue());
        }
        catch (IOException ex) {
            Logger.getLogger(SellerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
