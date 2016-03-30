package za.co.jericho.property.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.interceptors.AuditTrailInterceptor;
import za.co.jericho.interceptors.SecurityPermissionInterceptor;
import za.co.jericho.interceptors.UserActivityMonitorInterceptor;
import za.co.jericho.property.domain.Property;
import za.co.jericho.property.search.*;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

@Stateless
@Remote(ManagePropertyService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManagePropertyServiceBean extends AbstractServiceBean
implements ManagePropertyService {
    
    @SecurityPermission(serviceName = ServiceName.ADD_PROPERTY)
    @AuditTrail(serviceName = ServiceName.ADD_PROPERTY)
    @Override
    public Property addProperty(Property property) {
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
        return property;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_PROPERTY)
    @AuditTrail(serviceName = ServiceName.UPDATE_PROPERTY)
    @Override
    public Property updateProperty(Property property) {
        /* Validations */
        /* State validation */
        property.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(property)) {
            getEntityManager().merge(property);
        }
        return property;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_PROPERTY_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_PROPERTY_DELETED)
    @Override
    public Property markPropertyDeleted(Property property) {
        throw new DeleteNotSupportedException("Deleting a property is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_PROPERTIES)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_PROPERTIES)
    @Override
    public List<Property> searchProperties(PropertySearchCriteria propertySearchCriteria) {
        List<Property> properties = new ArrayList<>();
        if (propertySearchCriteria != null) {
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
        }
        else {
            throw new ServiceBeanException("Property search criteria not provided");
        }
        return properties;
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_PROPERTIES)
    @Override
    public Property findProperty(Object id) {
        return getEntityManager().find(Property.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_PROPERTIES)
    @Override
    public List<Property> findAllProperties() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Property.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}