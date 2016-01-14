package za.co.jericho.estateagent.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.estateagent.domain.EstateAgent;
import za.co.jericho.estateagent.search.*;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.interceptors.AuditTrailInterceptor;
import za.co.jericho.interceptors.SecurityPermissionInterceptor;
import za.co.jericho.interceptors.UserActivityMonitorInterceptor;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Stateless
@Remote(ManageEstateAgentService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManageEstateAgentServiceBean extends AbstractServiceBean 
implements ManageEstateAgentService{

    @SecurityPermission(serviceName = ServiceName.ADD_ESTATE_AGENT)
    @AuditTrail(serviceName = ServiceName.ADD_ESTATE_AGENT)
    @Override
    public EstateAgent addEstateAgent(EstateAgent estateAgent) {
        /* Validations */
        /* State validation */
        estateAgent.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(estateAgent)) {
            getEntityManager().persist(estateAgent);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        return estateAgent;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_ESTATE_AGENT)
    @AuditTrail(serviceName = ServiceName.UPDATE_ESTATE_AGENT)
    @Override
    public EstateAgent updateEstateAgent(EstateAgent estateAgent) {
        /* Validations */
        /* State validation */
        estateAgent.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(estateAgent)) {
            getEntityManager().merge(estateAgent);
        }
        return estateAgent;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_ESTATE_AGENT_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_ESTATE_AGENT_DELETED)
    @Override
    public EstateAgent markEstateAgentDeleted(EstateAgent estateAgent) {
        throw new DeleteNotSupportedException("Deleting an estate agent is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_ESTATE_AGENTS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_ESTATE_AGENTS)
    @Override
    public List<EstateAgent> searchEstateAgents(EstateAgentSearchCriteria 
        estateAgentSearchCriteria) {
        List<EstateAgent> estateAgents = new ArrayList<>();
        if (estateAgentSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchAttorneysStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchAttorneysStringBuilder.append("SELECT ea FROM EstateAgent ea ");
            searchAttorneysStringBuilder.append("WHERE ea.name like :name ");
            String name = stringConvertor.convertForDatabaseSearch
                (estateAgentSearchCriteria.getName(), estateAgentSearchCriteria.getSearchType());
            estateAgents = getEntityManager().createQuery(searchAttorneysStringBuilder.toString())
                .setParameter("name", name)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Estate agent search criteria not provided");
        }
        return estateAgents;
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_ESTATE_AGENTS)
    @Override
    public EstateAgent findEstateAgent(Object id) {
        return getEntityManager().find(EstateAgent.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_ESTATE_AGENTS)
    @Override
    public List<EstateAgent> findAllEstateAgents() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(EstateAgent.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}