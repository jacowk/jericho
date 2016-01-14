package za.co.jericho.account.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.account.domain.Account;
import za.co.jericho.account.search.AccountSearchCriteria;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
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

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
@Stateless
@Remote(ManageAccountService.class)
public class ManageAccountServiceBean extends AbstractServiceBean {

    /**
     * 
     * @param account
     * @return 
     */
    public Account addAccount(Account account) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(account.getCreatedBy(), ServiceName
            .ADD_ACCOUNT.getValue());
        
        /* Validations */
        /* State validation */
        account.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(account)) {
            getEntityManager().persist(account);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.ACCOUNT, //EntityName entityName
            ServiceName.ADD_ACCOUNT.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            account.toString(), //String description
            account.getCreatedBy())); //User currentUser
        return account;
    }

    /**
     * 
     * @param account
     * @return 
     */
    public Account updateAccount(Account account) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(account.getLastModifiedBy(), ServiceName
            .UPDATE_ACCOUNT.getValue());
        
        /* Validations */
        /* State validation */
        account.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(account)) {
            getEntityManager().merge(account);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.ACCOUNT, //EntityName entityName
            ServiceName.UPDATE_ACCOUNT.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            account.toString(), //String description
            account.getLastModifiedBy())); //User currentUser
        return account;
    }

    /**
     * 
     * @param account
     * @return 
     */
    public Account markAccountDeleted(Account account) {
        throw new DeleteNotSupportedException("Deleting an account is not supported");
    }

    /**
     * 
     * @param accountSearchCriteria
     * @return 
     */
    public Collection<Account> searchAcounts(AccountSearchCriteria accountSearchCriteria) {
        Collection<Account> accounts = new ArrayList<>();
        if (accountSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(accountSearchCriteria.getServiceUser(), 
                ServiceName.SEARCH_ACCOUNTS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchAccountsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchAccountsStringBuilder.append("SELECT a FROM Account a ");
            searchAccountsStringBuilder.append("WHERE a.name like :name ");
            String name = stringConvertor.convertForDatabaseSearch
                (accountSearchCriteria.getName(), accountSearchCriteria.getSearchType());
            accounts = getEntityManager().createQuery(searchAccountsStringBuilder.toString())
                .setParameter("name", name)
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.ACCOUNT, //String entityName
            ServiceName.SEARCH_ACCOUNTS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                accountSearchCriteria.toString(), //String description
                accountSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Account search criteria not provided");
        }
        return accounts;
    }
    
    public void calculateProfitOrLoss() {
        // TODO - implement ManageAccountServiceBean.calculateProfitOrLoss
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public Account findAccount(Object id) {
        return getEntityManager().find(Account.class, id);
    }
    
    public Collection<Account> findAllAccounts(){
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Account.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}