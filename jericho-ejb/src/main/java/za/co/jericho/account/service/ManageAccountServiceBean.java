package za.co.jericho.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import za.co.jericho.account.domain.Account;
import za.co.jericho.account.lookup.AccountType;
import za.co.jericho.account.search.AccountSearchCriteria;
import za.co.jericho.account.search.AccountTypeSearchCriteria;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
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

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
@Stateless
@Remote(ManageAccountService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManageAccountServiceBean extends AbstractServiceBean 
implements ManageAccountService{

    /**
     * 
     * @param account Account
     * @return Account
     */
    @SecurityPermission(serviceName = ServiceName.ADD_ACCOUNT)
    @AuditTrail(serviceName = ServiceName.ADD_ACCOUNT)
    @Override
    public Account addAccount(Account account) {
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
        return account;
    }

    /**
     * 
     * @param account Account
     * @return Account
     */
    @SecurityPermission(serviceName = ServiceName.UPDATE_ACCOUNT)
    @AuditTrail(serviceName = ServiceName.UPDATE_ACCOUNT)
    @Override
    public Account updateAccount(Account account) {
        /* Validations */
        /* State validation */
        account.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(account)) {
            getEntityManager().merge(account);
        }
        return account;
    }

    /**
     * 
     * @param account Account
     * @return Account
     */
    @SecurityPermission(serviceName = ServiceName.MARK_ACCOUNT_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_ACCOUNT_DELETED)
    @Override
    public Account markAccountDeleted(Account account) {
        throw new DeleteNotSupportedException("Deleting an account is not supported");
    }

    /**
     * 
     * @param accountSearchCriteria AccountSearchCriteria
     * @return Collection<Account>
     */
    @SecurityPermission(serviceName = ServiceName.SEARCH_ACCOUNTS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_ACCOUNTS)
    @Override
    public Collection<Account> searchAccounts(AccountSearchCriteria accountSearchCriteria) {
        Collection<Account> accounts = new ArrayList<>();
        if (accountSearchCriteria != null) {
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
        }
        else {
            throw new ServiceBeanException("Account search criteria not provided");
        }
        return accounts;
    }
    
    @Override
    public BigDecimal calculateProfitOrLoss() {
        // TODO - implement ManageAccountServiceBean.calculateProfitOrLoss
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_ACCOUNTS)
    @Override
    public Account findAccount(Object id) {
        return getEntityManager().find(Account.class, id);
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_ACCOUNTS)
    @Override
    public Collection<Account> findAllAccounts() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Account.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    /* Account Type Services */
    @SecurityPermission(serviceName = ServiceName.ADD_ACCOUNT_TYPE)
    @AuditTrail(serviceName = ServiceName.ADD_ACCOUNT_TYPE)
    @Override
    public AccountType addAccountType(AccountType accountType){
        /* Validations */
        /* State validation */
        accountType.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(accountType)) {
            getEntityManager().persist(accountType);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        return accountType;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_ACCOUNT_TYPE)
    @AuditTrail(serviceName = ServiceName.UPDATE_ACCOUNT_TYPE)
    @Override
    public AccountType updateAccountType(AccountType accountType) {
        /* Validations */
        /* State validation */
        accountType.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(accountType)) {
            getEntityManager().merge(accountType);
        }
        return accountType;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_ACCOUNT_TYPE_DELETED)
    @AuditTrail(serviceName = ServiceName.MARK_ACCOUNT_TYPE_DELETED)
    @Override
    public AccountType markAccountTypeDeleted(AccountType accountType) {
        throw new DeleteNotSupportedException("Deleting an account type is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_ACCOUNT_TYPES)
    @AuditTrail(serviceName = ServiceName.SEARCH_ACCOUNT_TYPES)
    public Collection<AccountType> searchAccountTypes(AccountTypeSearchCriteria 
        accountTypeSearchCriteria) {
        Collection<AccountType> accountTypes = new ArrayList<>();
        if (accountTypeSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchAccountsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchAccountsStringBuilder.append("SELECT at FROM AccountType at ");
            searchAccountsStringBuilder.append("WHERE at.description like :description ");
            String description = stringConvertor.convertForDatabaseSearch
                (accountTypeSearchCriteria.getDescription(), accountTypeSearchCriteria.getSearchType());
            accountTypes = getEntityManager().createQuery(searchAccountsStringBuilder.toString())
                .setParameter("description", description)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Account type search criteria not provided");
        }
        return accountTypes;
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_ACCOUNT_TYPES)
    @Override
    public AccountType findAccountType(Object id) {
        return getEntityManager().find(AccountType.class, id);
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_ACCOUNT_TYPES)
    @Override
    public Collection<AccountType> findAllAccountTypes() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(AccountType.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}