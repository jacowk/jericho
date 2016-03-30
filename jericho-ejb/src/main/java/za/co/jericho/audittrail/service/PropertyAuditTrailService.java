package za.co.jericho.audittrail.service;

import org.apache.log4j.LogManager;
import za.co.jericho.property.domain.PropertyAuditTrail;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.property.domain.Property;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-18
 */
public class PropertyAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Property property = null;
        if (entity instanceof Property) {
            property = (Property) entity;
        }
        else {
            return null;
        }
        PropertyAuditTrail propertyAuditTrail = new PropertyAuditTrail();
        propertyAuditTrail.setEntityId(property.getId());
        propertyAuditTrail.setErfNumber(property.getErfNumber());
        propertyAuditTrail.setPortionNumber(property.getPortionNumber());
        propertyAuditTrail.setPropertySize(property.getPropertySize());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        propertyAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_PROPERTY) {
            propertyAuditTrail.setAuditTrailDate(property.getCreateDate());
            propertyAuditTrail.setAuditTrailUser(property.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_PROPERTY) {
            propertyAuditTrail.setAuditTrailDate(property.getLastModifyDate());
            propertyAuditTrail.setAuditTrailUser(property.getLastModifiedBy());
        }
        propertyAuditTrail.setDeleted(property.isDeleted());
        return propertyAuditTrail;
    }
    
}
