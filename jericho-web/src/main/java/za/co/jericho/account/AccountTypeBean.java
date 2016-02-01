package za.co.jericho.account;

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
import za.co.jericho.account.lookup.AccountType;
import za.co.jericho.account.search.AccountTypeSearchCriteria;
import za.co.jericho.account.service.ManageAccountService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-01
 */
@ManagedBean(name = "accountTypeBean")
@SessionScoped
public class AccountTypeBean implements Serializable {
    
    private AccountType accountType;
    private AccountTypeSearchCriteria accountTypeSearchCriteria = new 
        AccountTypeSearchCriteria();
    private Collection<AccountType> accountTypes = null;
    @EJB
    private ManageAccountService manageAccountService;
    
    public AccountTypeBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountTypeSearchCriteria getAccountTypeSearchCriteria() {
        return accountTypeSearchCriteria;
    }

    public void setAccountTypeSearchCriteria(AccountTypeSearchCriteria accountTypeSearchCriteria) {
        this.accountTypeSearchCriteria = accountTypeSearchCriteria;
    }

    public Collection<AccountType> getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(Collection<AccountType> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public ManageAccountService getManageAccountService() {
        return manageAccountService;
    }

    public void setManageAccountService(ManageAccountService manageAccountService) {
        this.manageAccountService = manageAccountService;
    }
    
    /* Service calls */
    public AccountType prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AccountTypeBean: prepareAdd")
            .toString());
        accountType = new AccountType();
        return accountType;
    }
    
    public void addAccountType() {
        try {
            if (accountType != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                accountType.setCreatedBy(currentUser);
                accountType.setCreateDate(new Date());
                accountType = manageAccountService.addAccountType(accountType);
                if (!JsfUtil.isValidationFailed()) {
                    accountTypes = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("AccountTypeAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The AccountType was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateAccountType() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AccountTypeBean: updateAccountType").toString());
        try {
            if (accountType != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                accountType.setLastModifiedBy(currentUser);
                accountType.setLastModifyDate(new Date());
                accountType = manageAccountService.updateAccountType(accountType);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("AccountTypeUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteAccountType() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AccountTypeBean: deleteAccountType").toString());
        try {
            if (accountType != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                accountType.setLastModifiedBy(currentUser);
                accountType.setLastModifyDate(new Date());
                accountType = manageAccountService.markAccountTypeDeleted(accountType);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("AccountTypeDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    accountType = null; // Remove selection
                    accountType = null;
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
    
    public void searchAccountTypes() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AccountTypeBean: searchAccountTypes").toString());
        try {
            if (accountTypeSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                accountTypeSearchCriteria.setServiceUser(currentUser);
                accountTypes = manageAccountService.searchAccountTypes(accountTypeSearchCriteria);
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
}
