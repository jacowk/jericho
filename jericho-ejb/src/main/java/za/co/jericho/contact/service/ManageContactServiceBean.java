package za.co.jericho.contact.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;

import javax.ejb.Stateless;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.search.ContactSearchCriteria;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Stateless
@Remote(ManageContactService.class)
public class ManageContactServiceBean extends AbstractServiceBean
implements ManageContactService {

    /**
     * A service to add a contact
     * 
     * @param contact
     * @return 
     */
    @Override
    public Contact addContact(Contact contact) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(contact.getCreatedBy(), ServiceName.ADD_CONTACT.getValue());
        
        /* Validations */
        /* State validation */
        contact.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(contact)) {
            getEntityManager().persist(contact);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.CONTACT, //EntityName entityName
            ServiceName.ADD_CONTACT.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            contact.toString(), //String description
            contact.getCreatedBy())); //User currentUser
        return contact;
    }

    /**
     * 
     * @param contact
     * @return 
     */
    @Override
    public Contact updateContact(Contact contact) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(contact.getLastModifiedBy(), ServiceName.UPDATE_CONTACT.getValue());
        
        /* Validations */
        /* State validation */
        contact.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(contact)) {
            getEntityManager().merge(contact);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.CONTACT, //EntityName entityName
            ServiceName.UPDATE_CONTACT.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            contact.toString(), //String description
            contact.getLastModifiedBy())); //User currentUser
        return contact;
    }

    /**
     * 
     * @param contact
     * @return 
     */
    @Override
    public Contact markContactDeleted(Contact contact) {
        throw new DeleteNotSupportedException("Deleting a contact is not supported");
    }

    /**
     * 
     * @param contactSearchCriteria ContactSearchCriteria
     * @return  List
     */
    @Override
    public Collection<Contact> searchContacts(ContactSearchCriteria contactSearchCriteria) {
        Collection<Contact> contacts = new ArrayList<>();
        if (contactSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(contactSearchCriteria.getServiceUser(), 
                ServiceName.SEARCH_CONTACTS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchContactsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchContactsStringBuilder.append("SELECT c FROM Contact c ");
            searchContactsStringBuilder.append("WHERE c.firstname like :firstname ");
            searchContactsStringBuilder.append("AND c.surname like :surname ");
            searchContactsStringBuilder.append("AND c.surname like :surname ");
            searchContactsStringBuilder.append("AND c.workEmail like :workEmail ");
            searchContactsStringBuilder.append("AND c.personalEmail like :personalEmail ");
            searchContactsStringBuilder.append("AND c.idNumber = :idNumber ");
            searchContactsStringBuilder.append("AND c.passportNumber like :passportNumber ");
            String firstname = stringConvertor.convertForDatabaseSearch
                (contactSearchCriteria.getFirstname(), contactSearchCriteria.getSearchType());
            String surname = stringConvertor.convertForDatabaseSearch
                (contactSearchCriteria.getSurname(), contactSearchCriteria.getSearchType());
            String workEmail = stringConvertor.convertForDatabaseSearch
                (contactSearchCriteria.getWorkEmail(), contactSearchCriteria.getSearchType());
            String personalEmail = stringConvertor.convertForDatabaseSearch
                (contactSearchCriteria.getPersonalEmail(), contactSearchCriteria.getSearchType());
            String passportNumber = stringConvertor.convertForDatabaseSearch
                (contactSearchCriteria.getPassportNumber(), contactSearchCriteria.getSearchType());
            
            contacts = getEntityManager().createQuery(searchContactsStringBuilder.toString())
                .setParameter("firstname", firstname)
                .setParameter("surname", surname)
                .setParameter("workEmail", workEmail)
                .setParameter("personalEmail", personalEmail)
                .setParameter("idNumber", contactSearchCriteria.getIdNumber())
                .setParameter("passportNumber", passportNumber)
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.CONTACT, //String entityName
            ServiceName.SEARCH_CONTACTS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                contactSearchCriteria.toString(), //String description
                contactSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Contact search criteria not provided");
        }
        return contacts;
    }
    
    @Override
    public Contact findContact(Object id) {
        return getEntityManager().find(Contact.class, id);
    }

    @Override
    public Collection<Contact> findAllContacts() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Contact.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}