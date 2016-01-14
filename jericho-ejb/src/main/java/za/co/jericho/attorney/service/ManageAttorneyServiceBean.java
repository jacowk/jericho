package za.co.jericho.attorney.service;

import java.util.Collection;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
import za.co.jericho.attorney.domain.Attorney;
import za.co.jericho.attorney.search.AttorneySearchCriteria;
import za.co.jericho.attorney.search.AttorneySearchUnit;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.interceptors.AuditTrailInterceptor;
import za.co.jericho.interceptors.SecurityPermissionInterceptor;
import za.co.jericho.interceptors.UserActivityMonitorInterceptor;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

@Stateless
@Remote(ManageAttorneyService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManageAttorneyServiceBean extends AbstractServiceBean 
implements ManageAttorneyService {

    /**
     * 
     * @param attorney
     * @return 
     */
    @SecurityPermission(serviceName = ServiceName.ADD_ATTORNEY)
    @AuditTrail(serviceName = ServiceName.ADD_ATTORNEY)
    @Override
    public Attorney addAttorney(Attorney attorney) {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("ManageAttorneyServiceBean: addAttorney").toString());
        
        /* Validations */
        /* State validation */
        attorney.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(attorney)) {
            getEntityManager().persist(attorney);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        return attorney;
    }

    /**
     * 
     * @param attorney
     * @return 
     */
    @SecurityPermission(serviceName = ServiceName.UPDATE_ATTORNEY)
    @AuditTrail(serviceName = ServiceName.UPDATE_ATTORNEY)
    @Override
    public Attorney updateAttorney(Attorney attorney) {
        /* Validations */
        /* State validation */
        attorney.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(attorney)) {
            getEntityManager().merge(attorney);
        }
        return attorney;
    }

    /**
     * 
     * @param attorney
     * @return 
     */
    @SecurityPermission(serviceName = ServiceName.MARK_ATTORNEY_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_ATTORNEY_DELETED)
    @Override
    public Attorney markAttorneyDeleted(Attorney attorney) {
        throw new DeleteNotSupportedException("Deleting an attorney is not supported");
    }

    /**
     * Audit trailing is not required for searches
     * 
     * @param attorneySearchCriteria
     * @return 
     */
    @SecurityPermission(serviceName = ServiceName.SEARCH_ATTORNEYS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_ATTORNEYS)
    @Override
    public Collection<Attorney> searchAttorneys(AttorneySearchCriteria 
        attorneySearchCriteria) {
        Logger.getRootLogger().info("ManageAttorneyServiceBean: searchAttorneys");
        AttorneySearchUnit attorneySearchUnit = new AttorneySearchUnit(
            getEntityManager(), attorneySearchCriteria);
        attorneySearchUnit.run();
        return attorneySearchUnit.getAttorneys();
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_ATTORNEYS)
    @Override
    public Attorney findAttorney(Object id) {
        return getEntityManager().find(Attorney.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_ATTORNEYS)
    @Override
    public Collection<Attorney> findAllAttorneys() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Attorney.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}