package za.co.jericho.contact;

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
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.search.ContactSearchCriteria;
import za.co.jericho.contact.service.ManageContactService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-03-28
 */
@ManagedBean(name = "contactBean")
@SessionScoped
public class ContactBean implements Serializable {
    
    private Contact contact;
    private ContactSearchCriteria contactSearchCriteria = new ContactSearchCriteria();
    private Collection<Contact> contacts = null;
    @EJB
    private ManageContactService manageContactService;
    private String saCitizenValue;
    
    public ContactBean() {
        
    }
    
    @PostConstruct
    public void init() {
        updateSACitizenValue();
    }

    /* Getters and Setters */
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ContactSearchCriteria getContactSearchCriteria() {
        return contactSearchCriteria;
    }

    public void setContactSearchCriteria(ContactSearchCriteria contactSearchCriteria) {
        this.contactSearchCriteria = contactSearchCriteria;
    }

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

    public String getSaCitizenValue() {
        return saCitizenValue;
    }

    public void setSaCitizenValue(String saCitizenValue) {
        this.saCitizenValue = saCitizenValue;
    }
    
    /* Service calls */
    public Contact prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ContactBean: prepareAdd")
            .toString());
        contact = new Contact();
        return contact;
    }
    
    public void addContact() {
        try {
            if (contact != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                contact.setCreatedBy(currentUser);
                contact.setCreateDate(new Date());
                contact = manageContactService.addContact(contact);
                updateSACitizenValue();
                if (!JsfUtil.isValidationFailed()) {
                    contacts = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("ContactAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Contact was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateContact() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ContactBean: updateContact").toString());
        try {
            if (contact != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                contact.setLastModifiedBy(currentUser);
                contact.setLastModifyDate(new Date());
                contact = manageContactService.updateContact(contact);
                updateSACitizenValue();
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("ContactUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteContact() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ContactBean: deleteContact").toString());
        try {
            if (contact != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                contact.setLastModifiedBy(currentUser);
                contact.setLastModifyDate(new Date());
                contact = manageContactService.markContactDeleted(contact);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("ContactDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    contact = null; // Remove selection
                    contact = null;
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
    
    public void searchContacts() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ContactBean: searchContacts").toString());
        try {
            if (contactSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                contactSearchCriteria.setServiceUser(currentUser);
                contacts = manageContactService.searchContacts(contactSearchCriteria);
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    private void updateSACitizenValue() {
        if (contact != null) {
            if (contact.getSaCitizen()) {
                this.saCitizenValue = "Yes";
            }
            else {
                this.saCitizenValue = "No";
            }
        }
    }
    
}
