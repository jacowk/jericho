package za.co.jericho.propertyflip.service;

import za.co.jericho.audittrail.service.AbstractAuditTrailService;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.propertyflip.domain.PropertyFlipAuditTrail;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-18
 */
public class PropertyFlipAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        PropertyFlip propertyFlip = null;
        if (entity instanceof PropertyFlip) {
            propertyFlip = (PropertyFlip) entity;
        }
        else {
            return null;
        }
        PropertyFlipAuditTrail propertyFlipAuditTrail = new PropertyFlipAuditTrail();
        propertyFlipAuditTrail.setEntityId(propertyFlip.getId());
        propertyFlipAuditTrail.setReferenceNumber(propertyFlip.getReferenceNumber());
        propertyFlipAuditTrail.setTitleDeedNumber(propertyFlip.getTitleDeedNumber());
        propertyFlipAuditTrail.setCaseNumber(propertyFlip.getCaseNumber());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        propertyFlipAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_PROPERTY_FLIP) {
            propertyFlipAuditTrail.setAuditTrailDate(propertyFlip.getCreateDate());
            propertyFlipAuditTrail.setAuditTrailUser(propertyFlip.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_PROPERTY_FLIP) {
            propertyFlipAuditTrail.setAuditTrailDate(propertyFlip.getLastModifyDate());
            propertyFlipAuditTrail.setAuditTrailUser(propertyFlip.getLastModifiedBy());
        }
        propertyFlipAuditTrail.setDeleted(propertyFlip.isDeleted());
        return propertyFlipAuditTrail;
    }
    
}
