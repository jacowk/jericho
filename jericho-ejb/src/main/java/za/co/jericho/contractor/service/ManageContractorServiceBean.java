package za.co.jericho.contractor.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.contractor.domain.Contractor;
import za.co.jericho.contractor.search.ContractorSearchCriteria;
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

/**
 * @author Jaco Koekemoer
 * Date: 2015-10-14
 */
@Stateless
@Remote(ManageContractorService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManageContractorServiceBean extends AbstractServiceBean 
implements ManageContractorService{

    @SecurityPermission(serviceName = ServiceName.ADD_CONTRACTOR)
    @AuditTrail(serviceName = ServiceName.ADD_CONTRACTOR)
    @Override
    public Contractor addContractor(Contractor contractor) {
        /* Validations */
        /* State validation */
        contractor.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(contractor)) {
            getEntityManager().persist(contractor);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        return contractor;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_CONTRACTOR)
    @AuditTrail(serviceName = ServiceName.UPDATE_CONTRACTOR)
    @Override
    public Contractor updateContractor(Contractor contractor) {
        /* Validations */
        /* State validation */
        contractor.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(contractor)) {
            getEntityManager().merge(contractor);
        }
        return contractor;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_CONTRACTOR_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_CONTRACTOR_DELETED)
    @Override
    public Contractor markContractorDeleted(Contractor contractor) {
        throw new DeleteNotSupportedException("Deleting a contractor is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_CONTRACTORS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_CONTRACTORS)
    @Override
    public List<Contractor> searchContractors(ContractorSearchCriteria contractorSearchCriteria) {
        List<Contractor> contractors = new ArrayList<>();
        if (contractorSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchContactsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchContactsStringBuilder.append("SELECT c FROM Contractor c ");
            searchContactsStringBuilder.append("WHERE c.name like :name ");
            searchContactsStringBuilder.append("AND c.workDescription like :workDescription ");
//            searchContactsStringBuilder.append("AND c.contacts.firstname like :firstname ");
//            searchContactsStringBuilder.append("AND c.contacts.surname like :surname ");
//            searchContactsStringBuilder.append("AND c.contacts.workEmail like :workEmail ");
//            searchContactsStringBuilder.append("AND c.contacts.personalEmail like :personalEmail ");
            String name = stringConvertor.convertForDatabaseSearch
                (contractorSearchCriteria.getName(), contractorSearchCriteria.getSearchType());
            String workDescription = stringConvertor.convertForDatabaseSearch
                (contractorSearchCriteria.getWorkDescription(), contractorSearchCriteria.getSearchType());
            String firstname = "%%";
            String surname = "%%";
            String workEmail = "%%";
            String personalEmail = "%%";
            if (contractorSearchCriteria.getContactSearchCriteria() != null) {
                firstname = stringConvertor.convertForDatabaseSearch
                (contractorSearchCriteria.getContactSearchCriteria().getFirstname(), 
                contractorSearchCriteria.getSearchType());
                surname = stringConvertor.convertForDatabaseSearch
                    (contractorSearchCriteria.getContactSearchCriteria().getSurname(), 
                    contractorSearchCriteria.getSearchType());
                workEmail = stringConvertor.convertForDatabaseSearch
                    (contractorSearchCriteria.getContactSearchCriteria().getWorkEmail(), 
                    contractorSearchCriteria.getSearchType());
                personalEmail = stringConvertor.convertForDatabaseSearch
                    (contractorSearchCriteria.getContactSearchCriteria().getPersonalEmail(), 
                    contractorSearchCriteria.getSearchType());
            }
            contractors = getEntityManager().createQuery(searchContactsStringBuilder.toString())
                .setParameter("name", name)
                .setParameter("workDescription", workDescription)
//                .setParameter("firstname", firstname)
//                .setParameter("surname", surname)
//                .setParameter("workEmail", workEmail)
//                .setParameter("personalEmail", personalEmail)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Contractor search criteria not provided");
        }
        return contractors;
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_CONTRACTORS)
    @Override
    public Contractor findContractor(Object id) {
        return getEntityManager().find(Contractor.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_CONTRACTORS)
    @Override
    public List<Contractor> findAllContractors() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Contractor.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}