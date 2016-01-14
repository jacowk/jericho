package za.co.jericho.property.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.property.domain.Property;
import za.co.jericho.property.search.*;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.service.ManageSecurityUserService;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

@Stateless
@Remote(ManagePropertyService.class)
public class ManagePropertyServiceBean extends AbstractServiceBean
implements ManagePropertyService {
    
    @EJB
    private ManageSecurityUserService manageSecurityUserService;

    @Override
    public Property addProperty(Property property) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(property.getCreatedBy(), ServiceName.ADD_PROPERTY.getValue());
        
        /* Validations */
        /* State validation */
        property.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(property)) {
            getEntityManager().persist(property);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.PROPERTY, //EntityName entityName
            ServiceName.ADD_PROPERTY.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            property.toString(), //String description
            property.getCreatedBy())); //User currentUser
        return property;
    }

    @Override
    public Property updateProperty(Property property) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(property.getLastModifiedBy(), ServiceName.UPDATE_PROPERTY.getValue());
        
        /* Validations */
        /* State validation */
        property.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(property)) {
            getEntityManager().merge(property);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.PROPERTY, //EntityName entityName
            ServiceName.UPDATE_PROPERTY.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            property.toString(), //String description
            property.getLastModifiedBy())); //User currentUser
        return property;
    }

    @Override
    public Property markPropertyDeleted(Property property) {
        throw new DeleteNotSupportedException("Deleting a property is not supported");
    }

    @Override
    public List<Property> searchProperties(PropertySearchCriteria propertySearchCriteria) {
        List<Property> properties = new ArrayList<>();
        if (propertySearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(propertySearchCriteria.getServiceUser(), ServiceName.SEARCH_PROPERTIES.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT p FROM Property p ");
            if (propertySearchCriteria.getSelectedSearchBy().equals(PropertySearchBy.CASE_NUMBER.getValue())) {
                searchUsersStringBuilder.append("WHERE p.propertyFlip.caseNumber like :searchValue ");
                properties = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                    .setParameter("searchValue", propertySearchCriteria.getSearchValue())
                    .getResultList();
            }
            else if (propertySearchCriteria.getSelectedSearchBy().equals(PropertySearchBy.REFERENCE_NUMBER.getValue())) {
                searchUsersStringBuilder.append("WHERE p.propertyFlip.referenceNumber = :searchValue ");
                properties = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                    .setParameter("searchValue", Long.valueOf(propertySearchCriteria.getSearchValue()))
                    .getResultList();
            }
            else if (propertySearchCriteria.getSelectedSearchBy().equals(PropertySearchBy.ERF_NUMBER.getValue())) {
                searchUsersStringBuilder.append("WHERE p.erfNumber = :searchValue ");
                properties = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                    .setParameter("searchValue", Integer.valueOf(propertySearchCriteria.getSearchValue()))
                    .getResultList();
            }
            else if (propertySearchCriteria.getSelectedSearchBy().equals(PropertySearchBy.ADDRESS.getValue())) {
                searchUsersStringBuilder.append("WHERE p.address.addressLine1 like :searchValue ");
                searchUsersStringBuilder.append("OR p.address.addressLine2 like :searchValue ");
                searchUsersStringBuilder.append("OR p.address.addressLine3 like :searchValue ");
                searchUsersStringBuilder.append("OR p.address.addressLine4 like :searchValue ");
                searchUsersStringBuilder.append("OR p.address.addressLine5 like :searchValue ");
                String searchValue = stringConvertor.convertForDatabaseSearch
                    (propertySearchCriteria.getSearchValue(), null);
                properties = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                    .setParameter("searchValue", searchValue)
                    .getResultList();
            }
            else {
                /* Find all */
                properties = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                    .getResultList();
            }
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.PROPERTY, //String entityName
            ServiceName.SEARCH_PROPERTIES.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                propertySearchCriteria.toString(), //String description
                propertySearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Property search criteria not provided");
        }
        return properties;
    }
    
    @Override
    public Property findProperty(Object id) {
        return getEntityManager().find(Property.class, id);
    }

    @Override
    public List<Property> findAllProperties() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Property.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}