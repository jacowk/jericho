package za.co.jericho.propertyflip.service;

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
import za.co.jericho.exception.BeanServiceNotSupportedException;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.interceptors.AuditTrailInterceptor;
import za.co.jericho.interceptors.SecurityPermissionInterceptor;
import za.co.jericho.interceptors.UserActivityMonitorInterceptor;
import za.co.jericho.propertyflip.domain.Milestone;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.propertyflip.search.*;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Stateless
@Remote(ManagePropertyFlipService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManagePropertyFlipServiceBean extends AbstractServiceBean
implements ManagePropertyFlipService {

    @SecurityPermission(serviceName = ServiceName.ADD_PROPERTY_FLIP)
    @AuditTrail(serviceName = ServiceName.ADD_PROPERTY_FLIP)
    @Override
    public PropertyFlip addPropertyFlip(PropertyFlip propertyFlip) {
        /* Validations */
        /* State validation */
        propertyFlip.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(propertyFlip)) {
            getEntityManager().persist(propertyFlip);
        }
        return propertyFlip;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_PROPERTY_FLIP)
    @AuditTrail(serviceName = ServiceName.UPDATE_PROPERTY_FLIP)
    @Override
    public PropertyFlip updatePropertyFlip(PropertyFlip propertyFlip) {
        /* Validations */
        /* State validation */
        propertyFlip.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(propertyFlip)) {
            getEntityManager().merge(propertyFlip);
        }
        return propertyFlip;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_PROPERTY_FLIP_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_PROPERTY_FLIP_DELETED)
    @Override
    public PropertyFlip markPropertyFlipDeleted(PropertyFlip propertyFlip) {
        throw new DeleteNotSupportedException("Deleting a property flip is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_PROPERTY_FLIPS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_PROPERTY_FLIPS)
    @Override
    public List<PropertyFlip> searchPropertyFlips(PropertyFlipSearchCriteria 
        propertyFlipSearchCriteria) {
        PropertyFlipSearchUnit propertyFlipSearchUnit = new 
            PropertyFlipSearchUnit(getEntityManager(), propertyFlipSearchCriteria);
        propertyFlipSearchUnit.run();
        return propertyFlipSearchUnit.getPropertyFlips();
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_PROPERTY_FLIPS)
    @Override
    public PropertyFlip findPropertyFlip(Object id) {
        return getEntityManager().find(PropertyFlip.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_PROPERTY_FLIPS)
    @Override
    public List<PropertyFlip> findAllPropertyFlips() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(PropertyFlip.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SecurityPermission(serviceName = ServiceName.ADD_MILESTONE)
    @AuditTrail(serviceName = ServiceName.ADD_MILESTONE)
    @Override
    public Milestone addMilestone(Milestone milestone) {
        /* Validations */
        /* State validation */
        milestone.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(milestone)) {
            getEntityManager().persist(milestone);
        }
        return milestone;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_MILESTONE)
    @AuditTrail(serviceName = ServiceName.UPDATE_MILESTONE)
    @Override
    public Milestone updateMilestone(Milestone milestone) {
        /* Validations */
        /* State validation */
        milestone.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(milestone)) {
            getEntityManager().merge(milestone);
        }
        return milestone;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_MILESTONE_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_MILESTONE_DELETED)
    @Override
    public Milestone markMilestoneDeleted(Milestone milestone) {
        throw new DeleteNotSupportedException("Deleting milestones are not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_MILESTONES)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_MILESTONES)
    @Override
    public List searchMilestones(MilestoneSearchCriteria milestoneSearchCriteria) {
        throw new BeanServiceNotSupportedException("Searching milestones are not "
            + "supported yet. Milestones are eagerly loaded with PropertyFlips.");
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_MILESTONES)
    @Override
    public Milestone findMilestone(Object id) {
        return getEntityManager().find(Milestone.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_MILESTONES)
    @Override
    public List<Milestone> findAllMilestones() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Milestone.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}