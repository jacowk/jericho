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
import za.co.jericho.client.domain.Seller;
import za.co.jericho.client.service.ManageClientService;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.service.ManageContactService;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.propertyflip.service.ManagePropertyFlipService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.PathConstants;

/**
 * This managed bean is used with select-seller.xhtml, and is used to select
 * a contact, which will be the seller for a property flip.
 * 
 * @author Jaco Koekemoer
 * Date: 2016-05-28
 */
@ManagedBean(name = "selectSellerBean")
@SessionScoped
public class SelectSellerBean implements Serializable {
    
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
    public SelectSellerBean() {
        
    }
    
    /**
     * Initialization
     */
    @PostConstruct
    public void init() {
        LogManager.getRootLogger().info("SelectSellerBean: init");
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
    public void selectSellerFromDialog(Contact contact) {
        LogManager.getRootLogger().info("SelectSellerBean: selectSellerFromDialog 1: " + contact.getId());
        try {
            if (!isSellerAlreadySelected(contact)) {
                PropertyFlip propertyFlip = propertyFlipBean.getPropertyFlip();
                if (propertyFlip == null) {
                    JerichoWebUtil.addErrorMessage("The Property Flip is null. Cannot select a seller.");
                    return;
                }
                if (contact == null) {
                    JerichoWebUtil.addErrorMessage("A seller was not selected.");
                    return;
                }
                Seller currentSeller = propertyFlip.getSeller();
                if (currentSeller == null) {
                    addSeller(propertyFlip, contact);
                }
                else {
                    updateSeller(contact, currentSeller);
                }
                redirectToPropertyFlip();
            }
            else {
                StringBuilder errorMessage = new StringBuilder("The seller ");
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
     * Determine if the current seller is already selected
     * 
     * @param selectedContact
     * @return 
     */
    private boolean isSellerAlreadySelected(Contact selectedContact) {
        PropertyFlip propertyFlip = propertyFlipBean.getPropertyFlip();
        Seller currentSeller = propertyFlip.getSeller();
        if (currentSeller.getContact() != null) {
            if (Objects.equals(currentSeller.getContact().getId(), selectedContact.getId())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add a new seller
     * 
     * @param propertyFlip
     * @param contact 
     */
    private void addSeller(PropertyFlip propertyFlip, Contact contact) {
        SessionServices sessionServices = SessionServices.getInstance();
        User currentUser = sessionServices.getUserFromSession();
        Seller seller = new Seller();
        seller.setContact(contact);
        seller.setPropertyFlip(propertyFlip);
        seller.setCreateDate(new Date());
        seller.setCreatedBy(currentUser);
        manageClientService.addSeller(seller);
        JerichoWebUtil.addSuccessMessage(ResourceBundle
            .getBundle("/JerichoWebBundle")
            .getString("SellerAdded"));
    }
    
    /**
     * Update a current seller
     * 
     * @param contact
     * @param currentSeller 
     */
    private void updateSeller(Contact contact, Seller currentSeller) {
        SessionServices sessionServices = SessionServices.getInstance();
        User currentUser = sessionServices.getUserFromSession();
        currentSeller.setContact(contact);
        currentSeller.setLastModifyDate(new Date());
        currentSeller.setLastModifiedBy(currentUser);
        manageClientService.updateSeller(currentSeller);
        JerichoWebUtil.addSuccessMessage(ResourceBundle
            .getBundle("/JerichoWebBundle")
            .getString("SellerUpdated"));
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
