package za.co.jericho.bank;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.LogManager;
import za.co.jericho.bank.domain.Bank;
import za.co.jericho.bank.search.BankSearchCriteria;
import za.co.jericho.bank.service.ManageBankService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-05
 */
@ManagedBean(name = "bankBean")
@SessionScoped
public class BankBean implements Serializable {
    
    private Bank bank;
    private BankSearchCriteria bankSearchCriteria = new BankSearchCriteria();
    private Collection<Bank> banks = null;
    @EJB
    private ManageBankService manageBankService;
    
    public BankBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public BankSearchCriteria getBankSearchCriteria() {
        return bankSearchCriteria;
    }

    public void setBankSearchCriteria(BankSearchCriteria bankSearchCriteria) {
        this.bankSearchCriteria = bankSearchCriteria;
    }

    public Collection<Bank> getBanks() {
        return banks;
    }

    public void setBanks(Collection<Bank> banks) {
        this.banks = banks;
    }

    public ManageBankService getManageBankService() {
        return manageBankService;
    }

    public void setManageBankService(ManageBankService manageBankService) {
        this.manageBankService = manageBankService;
    }
    
    /* Service calls */
    public Bank prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("BankBean: prepareAdd")
            .toString());
        bank = new Bank();
        return bank;
    }
    
    public void addBank() {
        try {
            if (bank != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                bank.setCreatedBy(currentUser);
                bank.setCreateDate(new Date());
                bank = manageBankService.addBank(bank);
                if (!JsfUtil.isValidationFailed()) {
                    banks = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("BankAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Bank was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateBank() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("BankBean: updateBank").toString());
        try {
            if (bank != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                bank.setLastModifiedBy(currentUser);
                bank.setLastModifyDate(new Date());
                bank = manageBankService.updateBank(bank);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("BankUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteBank() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("BankBean: deleteBank").toString());
        try {
            if (bank != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                bank.setLastModifiedBy(currentUser);
                bank.setLastModifyDate(new Date());
                bank = manageBankService.markBankDeleted(bank);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("BankDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    bank = null; // Remove selection
                    bank = null;
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
    
    public void searchBanks() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("BankBean: searchBanks").toString());
        try {
            if (bankSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                bankSearchCriteria.setServiceUser(currentUser);
                banks = manageBankService.searchBanks(bankSearchCriteria);
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
