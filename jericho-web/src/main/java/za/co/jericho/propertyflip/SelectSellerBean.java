package za.co.jericho.propertyflip;

import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.LogManager;
import org.primefaces.context.RequestContext;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.service.ManageContactService;

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
    
    /* Services */
    public void selectSellerFromDialog(Contact contact) {
        LogManager.getRootLogger().info("SelectSellerBean: selectSellerFromDialog 1: " + contact.getId());
//        RequestContext.getCurrentInstance().execute("PF('SellerSelectDialog').hide()");
        RequestContext.getCurrentInstance().closeDialog(contact);
    }
    
}
