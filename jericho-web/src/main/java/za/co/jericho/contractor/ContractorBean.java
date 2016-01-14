package za.co.jericho.contractor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.LogManager;
import za.co.jericho.contractor.domain.Contractor;
import za.co.jericho.contractor.domain.Contractor;
import za.co.jericho.contractor.search.ContractorSearchCriteria;
import za.co.jericho.contractor.service.ManageContractorService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-29
 */
@ManagedBean(name = "contractorBean")
@SessionScoped
public class ContractorBean implements Serializable {
    
    private Contractor contractor;
    private ContractorSearchCriteria contractorSearchCriteria = new 
        ContractorSearchCriteria();
    private List<Contractor> contractors = null;
    @EJB
    private ManageContractorService manageContractorService;
    
    public ContractorBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    /* Getters and Setters */
    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public ContractorSearchCriteria getContractorSearchCriteria() {
        return contractorSearchCriteria;
    }

    public void setContracorSearchCriteria(ContractorSearchCriteria 
        contractorSearchCriteria) {
        this.contractorSearchCriteria = contractorSearchCriteria;
    }

    public List<Contractor> getContractors() {
        return contractors;
    }

    public void setContractors(List<Contractor> contractors) {
        this.contractors = contractors;
    }

    public ManageContractorService getManageContractorService() {
        return manageContractorService;
    }

    public void setManageContractorService(ManageContractorService manageContractorService) {
        this.manageContractorService = manageContractorService;
    }

    /* Service calls */
    public Contractor prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ContractorBean: prepareAdd")
            .toString());
        contractor = new Contractor();
        return contractor;
    }
    
    public void addContractor() {
        try {
            if (contractor != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                contractor.setCreatedBy(currentUser);
                contractor.setCreateDate(new Date());
                contractor = manageContractorService.addContractor(contractor);
                if (!JsfUtil.isValidationFailed()) {
                    contractors = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("ContractorAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Contractor was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateContractor() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ContractorBean: updateContractor").toString());
        try {
            if (contractor != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                contractor.setLastModifiedBy(currentUser);
                contractor.setLastModifyDate(new Date());
                contractor = manageContractorService.updateContractor(contractor);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("ContractorUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteContractor() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ContractorBean: deleteContractor").toString());
        try {
            if (contractor != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                contractor.setLastModifiedBy(currentUser);
                contractor.setLastModifyDate(new Date());
                contractor = manageContractorService.markContractorDeleted(contractor);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("ContractorDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    contractor = null; // Remove selection
                    contractor = null;
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
    
    public void searchContractors() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ContractorBean: searchContractors").toString());
        try {
            if (contractorSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                contractorSearchCriteria.setServiceUser(currentUser);
                contractors = manageContractorService.searchContractors(contractorSearchCriteria);
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
