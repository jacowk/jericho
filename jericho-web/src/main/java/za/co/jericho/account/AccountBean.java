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
import za.co.jericho.account.domain.Account;
import za.co.jericho.account.search.AccountSearchCriteria;
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
@ManagedBean(name = "accountBean")
@SessionScoped
public class AccountBean implements Serializable {
    
    private Account account;
    private AccountSearchCriteria accountSearchCriteria = new 
        AccountSearchCriteria();
    private Collection<Account> accounts = null;
    @EJB
    private ManageAccountService manageAccountService;
    
    public AccountBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AccountSearchCriteria getAccountSearchCriteria() {
        return accountSearchCriteria;
    }

    public void setAccountSearchCriteria(AccountSearchCriteria accountSearchCriteria) {
        this.accountSearchCriteria = accountSearchCriteria;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public ManageAccountService getManageAccountService() {
        return manageAccountService;
    }

    public void setManageAccountService(ManageAccountService manageAccountService) {
        this.manageAccountService = manageAccountService;
    }
    
    /* Service calls */
    public Account prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AccountBean: prepareAdd")
            .toString());
        account = new Account();
        return account;
    }
    
    public void addAccount() {
        try {
            if (account != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                account.setCreatedBy(currentUser);
                account.setCreateDate(new Date());
                account = manageAccountService.addAccount(account);
                if (!JsfUtil.isValidationFailed()) {
                    accounts = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("AccountAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Account was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateAccount() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AccountBean: updateAccount").toString());
        try {
            if (account != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                account.setLastModifiedBy(currentUser);
                account.setLastModifyDate(new Date());
                account = manageAccountService.updateAccount(account);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("AccountUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteAccount() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AccountBean: deleteAccount").toString());
        try {
            if (account != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                account.setLastModifiedBy(currentUser);
                account.setLastModifyDate(new Date());
                account = manageAccountService.markAccountDeleted(account);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("AccountDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    account = null; // Remove selection
                    account = null;
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
    
    public void searchAccounts() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("AccountBean: searchAccounts").toString());
        try {
            if (accountSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                accountSearchCriteria.setServiceUser(currentUser);
                accounts = manageAccountService.searchAccounts(accountSearchCriteria);
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
