package za.co.jericho.contact.service;

import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.lookup.MaritalStatus;
import za.co.jericho.contact.search.ContactSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-06
 */
@Remote
public interface ManageContactService {
    
    public Contact addContact(Contact contact);

    public Contact updateContact(Contact contact);
    
    public Contact markContactDeleted(Contact contact);
    
    public Collection<Contact> searchContacts(ContactSearchCriteria contactSearchCriteria);
    
    public Contact findContact(Object id);
    
    public Collection<Contact> findAllContacts();
    
    public MaritalStatus addMaritalStatus(MaritalStatus maritalStatus);

    public MaritalStatus updateMaritalStatus(MaritalStatus maritalStatus);
    
    public MaritalStatus markMaritalStatusDeleted(MaritalStatus maritalStatus);
    
    public MaritalStatus findMaritalStatus(Object id);
    
    public Collection<MaritalStatus> findAllMaritalStatusses();
    
}
