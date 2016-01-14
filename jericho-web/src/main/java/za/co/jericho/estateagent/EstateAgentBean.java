package za.co.jericho.estateagent;

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
import za.co.jericho.estateagent.domain.EstateAgent;
import za.co.jericho.estateagent.search.EstateAgentSearchCriteria;
import za.co.jericho.estateagent.service.ManageEstateAgentService;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-30
 */
@ManagedBean(name = "estateAgentBean")
@SessionScoped
public class EstateAgentBean implements Serializable {
    
    private EstateAgent estateAgent;
    private EstateAgentSearchCriteria estateAgentSearchCriteria = new EstateAgentSearchCriteria();
    private List<EstateAgent> estateAgents = null;
    @EJB
    private ManageEstateAgentService manageEstateAgentService;
    
    public EstateAgentBean() {
        
    }
    
    @PostConstruct
    public void init() {
        
    }

    /* Getters and Setters */
    public EstateAgent getEstateAgent() {
        return estateAgent;
    }

    public void setEstateAgent(EstateAgent estateAgent) {
        this.estateAgent = estateAgent;
    }

    public EstateAgentSearchCriteria getEstateAgentSearchCriteria() {
        return estateAgentSearchCriteria;
    }

    public void setEstateAgentSearchCriteria(EstateAgentSearchCriteria 
        estateAgentSearchCriteria) {
        this.estateAgentSearchCriteria = estateAgentSearchCriteria;
    }

    public List<EstateAgent> getEstateAgents() {
        return estateAgents;
    }

    public void setEstateAgents(List<EstateAgent> estateAgents) {
        this.estateAgents = estateAgents;
    }

    public ManageEstateAgentService getManageEstateAgentService() {
        return manageEstateAgentService;
    }

    public void setManageEstateAgentService(ManageEstateAgentService manageEstateAgentService) {
        this.manageEstateAgentService = manageEstateAgentService;
    }
    
    /* Service calls */
    public EstateAgent prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("EstateAgentBean: prepareAdd")
            .toString());
        estateAgent = new EstateAgent();
        return estateAgent;
    }
    
    public void addEstateAgent() {
        try {
            if (estateAgent != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                estateAgent.setCreatedBy(currentUser);
                estateAgent.setCreateDate(new Date());
                estateAgent = manageEstateAgentService.addEstateAgent(estateAgent);
                if (!JsfUtil.isValidationFailed()) {
                    estateAgents = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("EstateAgentAdded"));
            }
            else
            {
                JerichoWebUtil.addErrorMessage("Error occured. The Estate Agent was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void updateEstateAgent() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("EstateAgentBean: updateEstateAgent").toString());
        try {
            if (estateAgent != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                estateAgent.setLastModifiedBy(currentUser);
                estateAgent.setLastModifyDate(new Date());
                estateAgent = manageEstateAgentService.updateEstateAgent(estateAgent);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("EstateAgentUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteEstateAgent() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("EstateAgentBean: deleteEstateAgent").toString());
        try {
            if (estateAgent != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                estateAgent.setLastModifiedBy(currentUser);
                estateAgent.setLastModifyDate(new Date());
                estateAgent = manageEstateAgentService.markEstateAgentDeleted(estateAgent);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("EstateAgentDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    estateAgent = null; // Remove selection
                    estateAgent = null;
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
    
    public void searchEstateAgents() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("EstateAgentBean: searchEstateAgents").toString());
        try {
            if (estateAgentSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                estateAgentSearchCriteria.setServiceUser(currentUser);
                estateAgents = manageEstateAgentService.searchEstateAgents(estateAgentSearchCriteria);
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
