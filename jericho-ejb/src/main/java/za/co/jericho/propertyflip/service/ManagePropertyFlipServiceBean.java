package za.co.jericho.propertyflip.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.BeanServiceNotSupportedException;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
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
public class ManagePropertyFlipServiceBean extends AbstractServiceBean
implements ManagePropertyFlipService {

    @Override
    public PropertyFlip addPropertyFlip(PropertyFlip propertyFlip) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(propertyFlip.getCreatedBy(), 
            ServiceName.ADD_PROPERTY_FLIP.getValue());
        
        /* Validations */
        /* State validation */
        propertyFlip.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(propertyFlip)) {
            getEntityManager().persist(propertyFlip);
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.PROPERTY_FLIP, //EntityName entityName
            ServiceName.ADD_PROPERTY_FLIP.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            propertyFlip.toString(), //String description
            propertyFlip.getCreatedBy())); //User currentUser
        return propertyFlip;
    }

    @Override
    public PropertyFlip updatePropertyFlip(PropertyFlip propertyFlip) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(propertyFlip.getLastModifiedBy(), 
            ServiceName.UPDATE_PROPERTY_FLIP.getValue());
        
        /* Validations */
        /* State validation */
        propertyFlip.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(propertyFlip)) {
            getEntityManager().merge(propertyFlip);
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.PROPERTY_FLIP, //EntityName entityName
            ServiceName.UPDATE_PROPERTY_FLIP.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            propertyFlip.toString(), //String description
            propertyFlip.getLastModifiedBy())); //User currentUser
        return propertyFlip;
    }

    @Override
    public PropertyFlip markPropertyFlipDeleted(PropertyFlip propertyFlip) {
        throw new DeleteNotSupportedException("Deleting a property flip is not supported");
    }

    @Override
    public List<PropertyFlip> searchPropertyFlips(PropertyFlipSearchCriteria propertyFlipSearchCriteria) {
        List<PropertyFlip> propertyFlips = new ArrayList<>();
        if (propertyFlipSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(propertyFlipSearchCriteria.getServiceUser(), 
                ServiceName.SEARCH_PROPERTY_FLIPS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
//            User user = userSearchCriteria.getUser();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT pf FROM PropetyFlip pf ");
            searchUsersStringBuilder.append("WHERE pf.referenceNumber like :referenceNumber ");
            searchUsersStringBuilder.append("AND pf.titleDeedNumber like :titleDeedNumber ");
            searchUsersStringBuilder.append("AND pf.caseNumber like :caseNumber ");
            String referenceNumber = stringConvertor.convertForDatabaseSearch
                (propertyFlipSearchCriteria.getReferenceNumber(), 
                propertyFlipSearchCriteria.getSearchType());
            String titleDeedNumber = stringConvertor.convertForDatabaseSearch
                (propertyFlipSearchCriteria.getTitleDeedNumber(), 
                propertyFlipSearchCriteria.getSearchType());
            String caseNumber = stringConvertor.convertForDatabaseSearch
                (propertyFlipSearchCriteria.getCaseNumber(), 
                propertyFlipSearchCriteria.getSearchType());
            propertyFlips = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("referenceNumber", referenceNumber)
                .setParameter("titleDeedNumber", titleDeedNumber)
                .setParameter("caseNumber", caseNumber)
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.PROPERTY_FLIP, //String entityName
                ServiceName.SEARCH_PROPERTY_FLIPS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                propertyFlipSearchCriteria.toString(), //String description
                propertyFlipSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Property Flip search criteria not provided");
        }
        return propertyFlips;
    }
    
    @Override
    public PropertyFlip findPropertyFlip(Object id) {
        return getEntityManager().find(PropertyFlip.class, id);
    }

    @Override
    public List<PropertyFlip> findAllPropertyFlips() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(PropertyFlip.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Milestone addMilestone(Milestone milestone) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(milestone.getCreatedBy(), 
            ServiceName.ADD_MILESTONE.getValue());
        
        /* Validations */
        /* State validation */
        milestone.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(milestone)) {
            getEntityManager().persist(milestone);
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.MILESTONE, //EntityName entityName
            ServiceName.ADD_MILESTONE.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            milestone.toString(), //String description
            milestone.getCreatedBy())); //User currentUser
        return milestone;
    }

    @Override
    public Milestone updateMilestone(Milestone milestone) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(milestone.getLastModifiedBy(), 
            ServiceName.UPDATE_MILESTONE.getValue());
        
        /* Validations */
        /* State validation */
        milestone.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(milestone)) {
            getEntityManager().merge(milestone);
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.MILESTONE, //EntityName entityName
            ServiceName.UPDATE_MILESTONE.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            milestone.toString(), //String description
            milestone.getLastModifiedBy())); //User currentUser
        return milestone;
    }

    @Override
    public Milestone markMilestoneDeleted(Milestone milestone) {
        throw new DeleteNotSupportedException("Deleting milestones are not supported");
    }

    @Override
    public List searchMilestones(MilestoneSearchCriteria milestoneSearchCriteria) {
        throw new BeanServiceNotSupportedException("Searching milestones are not "
            + "supported yet. Milestones are eagerly loaded with PropertyFlips.");
    }
    
    @Override
    public Milestone findMilestone(Object id) {
        return getEntityManager().find(Milestone.class, id);
    }

    @Override
    public List<Milestone> findAllMilestones() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Milestone.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}