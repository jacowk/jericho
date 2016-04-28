package za.co.jericho.contact.service;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remote;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.lookup.MaritalStatus;
import za.co.jericho.contact.search.ContactSearchCriteria;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.interceptors.AuditTrailInterceptor;
import za.co.jericho.interceptors.SecurityPermissionInterceptor;
import za.co.jericho.interceptors.UserActivityMonitorInterceptor;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Stateless
@Remote(ManageContactService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManageContactServiceBean extends AbstractServiceBean
implements ManageContactService {

    @SecurityPermission(serviceName = ServiceName.ADD_CONTACT)
    @AuditTrail(serviceName = ServiceName.ADD_CONTACT)
    @Override
    public Contact addContact(Contact contact) {
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
        return contact;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_CONTACT)
    @AuditTrail(serviceName = ServiceName.UPDATE_CONTACT)
    @Override
    public Contact updateContact(Contact contact) {
        /* Validations */
        /* State validation */
        contact.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(contact)) {
            getEntityManager().merge(contact);
        }
        return contact;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_CONTACT_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_CONTACT_DELETED)
    @Override
    public Contact markContactDeleted(Contact contact) {
        throw new DeleteNotSupportedException("Deleting a contact is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_CONTACTS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_CONTACTS)
    @Override
    public Collection<Contact> searchContacts(ContactSearchCriteria contactSearchCriteria) {
        Collection<Contact> contacts = new ArrayList<>();
        if (contactSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchContactsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            searchContactsStringBuilder.append("SELECT c FROM Contact c ");
            searchContactsStringBuilder.append("WHERE c.firstname like :firstname ");
            searchContactsStringBuilder.append("AND c.surname like :surname ");
            String firstname = stringConvertor.convertForDatabaseSearch
                (contactSearchCriteria.getFirstname(), contactSearchCriteria.getSearchType());
            String surname = stringConvertor.convertForDatabaseSearch
                (contactSearchCriteria.getSurname(), contactSearchCriteria.getSearchType());            
            contacts = getEntityManager().createQuery(searchContactsStringBuilder.toString())
                .setParameter("firstname", firstname)
                .setParameter("surname", surname)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Contact search criteria not provided");
        }
        return contacts;
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_CONTACTS)
    @Override
    public Contact findContact(Object id) {
        return getEntityManager().find(Contact.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_CONTACTS)
    @Override
    public Collection<Contact> findAllContacts() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Contact.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public MaritalStatus addMaritalStatus(MaritalStatus maritalStatus) {
        /* Validations */
        /* State validation */
        maritalStatus.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(maritalStatus)) {
            getEntityManager().persist(maritalStatus);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        return maritalStatus;
    }

    @Override
    public MaritalStatus updateMaritalStatus(MaritalStatus maritalStatus) {
        /* Validations */
        /* State validation */
        maritalStatus.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(maritalStatus)) {
            getEntityManager().merge(maritalStatus);
        }
        return maritalStatus;
    }

    @Override
    public MaritalStatus markMaritalStatusDeleted(MaritalStatus maritalStatus) {
        throw new DeleteNotSupportedException("Deleting a marital status is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_CONTACTS)
    @Override
    public MaritalStatus findMaritalStatus(Object id) {
        return getEntityManager().find(MaritalStatus.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_CONTACTS)
    @Override
    public Collection<MaritalStatus> findAllMaritalStatusses() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(MaritalStatus.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}