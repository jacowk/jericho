package za.co.jericho.bank.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
import za.co.jericho.bank.domain.Bank;
import za.co.jericho.bank.search.BankSearchCriteria;
import za.co.jericho.common.service.AbstractServiceBean;
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
@Remote(ManageBankService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManageBankServiceBean extends AbstractServiceBean
implements ManageBankService{

    @SecurityPermission(serviceName = ServiceName.ADD_BANK)
    @AuditTrail(serviceName = ServiceName.ADD_BANK)
    @Override
    public Bank addBank(Bank bank) {
        /* Validations */
        /* State validation */
        bank.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(bank)) {
            getEntityManager().persist(bank);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        return bank;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_BANK)
    @AuditTrail(serviceName = ServiceName.UPDATE_BANK)
    @Override
    public Bank updateBank(Bank bank) {
        /* Validations */
        /* State validation */
        bank.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(bank)) {
            getEntityManager().merge(bank);
        }
        return bank;
    }
    
    @SecurityPermission(serviceName = ServiceName.MARK_BANK_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_BANK_DELETED)
    @Override
    public Bank markBankDeleted(Bank bank) {
        throw new DeleteNotSupportedException("Deleting a bank is not supported");
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_BANKS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_BANKS)
    @Override
    public Collection<Bank> searchBanks(BankSearchCriteria bankSearchCriteria) {
        Collection<Bank> contacts = new ArrayList<>();
        if (bankSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchContactsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchContactsStringBuilder.append("SELECT b FROM Bank b ");
            searchContactsStringBuilder.append("WHERE b.name like :name ");
            String name = stringConvertor.convertForDatabaseSearch
                (bankSearchCriteria.getName(), bankSearchCriteria.getSearchType());
            
            contacts = getEntityManager().createQuery(searchContactsStringBuilder.toString())
                .setParameter("name", name)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Contact search criteria not provided");
        }
        return contacts;
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_BANKS)
    @Override
    public Bank findBank(Object id) {
        return getEntityManager().find(Bank.class, id);
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_BANKS)
    @Override
    public Collection<Bank> findAllBanks() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Bank.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}