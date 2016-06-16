package za.co.jericho.propertyflip;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
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
import za.co.jericho.client.domain.Purchaser;
import za.co.jericho.client.service.ManageClientService;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.service.ManageContactService;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.PathConstants;

/**
 * This managed bean is used with select-purchaser.xhtml, and is used to select
 * a contact, which will be the purchaser for a property flip.
 * 
 * @author Jaco Koekemoer
 * Date: 2016-05-28
 */
@ManagedBean(name = "selectPurchaserBean")
@SessionScoped
public class SelectPurchaserBean implements Serializable {
    
    private Collection<Contact> contacts;
    @EJB
    private ManageContactService manageContactService;
    @ManagedProperty(value = "#{propertyFlipBean}")
    private PropertyFlipBean propertyFlipBean;
    @EJB
    private ManageClientService manageClientService;
    
    /**
     * Constructor
     */
    public SelectPurchaserBean() {
        
    }
    
    /**
     * Initialization
     */
    @PostConstruct
    public void init() {
        LogManager.getRootLogger().info("SelectPurchaserBean: init");
        contacts = manageContactService.findAllContacts();
    }

    /* Getters and setters */
    public Collection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }
    
    public ManageContactService getManageContactService() {
        return manageContactService;
    }

    public void setManageContactService(ManageContactService manageContactService) {
        this.manageContactService = manageContactService;
    }

    public PropertyFlipBean getPropertyFlipBean() {
        return propertyFlipBean;
    }

    public void setPropertyFlipBean(PropertyFlipBean propertyFlipBean) {
        this.propertyFlipBean = propertyFlipBean;
    }
    
    /* Services */
    public void selectPurchaserFromDialog(Contact contact) {
        LogManager.getRootLogger().info("SelectPurchaserBean: selectPurchaserFromDialog 1: " + contact.getId());
        try {
            if (!isPurchaserAlreadySelected(contact)) {
                PropertyFlip propertyFlip = propertyFlipBean.getPropertyFlip();
                if (propertyFlip == null) {
                    JerichoWebUtil.addErrorMessage("The Property Flip is null. Cannot select a purchaser.");
                    return;
                }
                if (contact == null) {
                    JerichoWebUtil.addErrorMessage("A purchaser was not selected.");
                    return;
                }
                Purchaser currentPurchaser = propertyFlip.getPurchaser();
                if (currentPurchaser == null) {
                    addPurchaser(propertyFlip, contact);
                }
                else {
                    updatePurchaser(contact, currentPurchaser);
                }
                redirectToPropertyFlip();
            }
            else {
                StringBuilder errorMessage = new StringBuilder("The purchaser ");
                errorMessage.append(contact.getFirstname());
                errorMessage.append(" ");
                errorMessage.append(contact.getSurname());
                errorMessage.append(" (");
                errorMessage.append(contact.getId());
                errorMessage.append(") is already selected for the property.");
                LogManager.getRootLogger().error(errorMessage.toString());
                JerichoWebUtil.addErrorMessage(errorMessage.toString());
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    /**
     * Determine if the current purchaser is already selected
     * 
     * @param selectedContact
     * @return 
     */
    private boolean isPurchaserAlreadySelected(Contact selectedContact) {
        PropertyFlip propertyFlip = propertyFlipBean.getPropertyFlip();
        Purchaser currentPurchaser = propertyFlip.getPurchaser();
        if (currentPurchaser.getContact() != null) {
            if (Objects.equals(currentPurchaser.getContact().getId(), selectedContact.getId())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add a new purchaser
     * 
     * @param propertyFlip
     * @param contact 
     */
    private void addPurchaser(PropertyFlip propertyFlip, Contact contact) {
        SessionServices sessionServices = SessionServices.getInstance();
        User currentUser = sessionServices.getUserFromSession();
        Purchaser purchaser = new Purchaser();
        purchaser.setContact(contact);
        purchaser.setPropertyFlip(propertyFlip);
        purchaser.setCreateDate(new Date());
        purchaser.setCreatedBy(currentUser);
        manageClientService.addPurchaser(purchaser);
        JerichoWebUtil.addSuccessMessage(ResourceBundle
            .getBundle("/JerichoWebBundle")
            .getString("PurchaserAdded"));
    }
    
    /**
     * Update a current purchaser
     * 
     * @param contact
     * @param currentPurchaser 
     */
    private void updatePurchaser(Contact contact, Purchaser currentPurchaser) {
        SessionServices sessionServices = SessionServices.getInstance();
        User currentUser = sessionServices.getUserFromSession();
        currentPurchaser.setContact(contact);
        currentPurchaser.setLastModifyDate(new Date());
        currentPurchaser.setLastModifiedBy(currentUser);
        manageClientService.updatePurchaser(currentPurchaser);
        JerichoWebUtil.addSuccessMessage(ResourceBundle
            .getBundle("/JerichoWebBundle")
            .getString("PurchaserUpdated"));
    }
    
    /**
     * Redirect the page to the property flip page
     */
    public void redirectToPropertyFlip() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + 
                PathConstants.MANAGE_PROPERTY_FLIP_PATH.getValue());
        }
        catch (IOException ex) {
            Logger.getLogger(PropertyFlipBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
