package za.co.jericho.audittrail.service;

import za.co.jericho.address.domain.GreaterArea;
import za.co.jericho.address.domain.GreaterAreaAuditTrail;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-16
 */
public class GreaterAreaAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        GreaterArea greaterArea = null;
        if (entity instanceof GreaterArea) {
            greaterArea = (GreaterArea) entity;
        }
        else {
            return null;
        }
        GreaterAreaAuditTrail greaterAreaAuditTrail = new GreaterAreaAuditTrail();
        greaterAreaAuditTrail.setEntityId(greaterArea.getId());
        greaterAreaAuditTrail.setName(greaterArea.getName());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        greaterAreaAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_GREATER_AREA) {
            greaterAreaAuditTrail.setAuditTrailDate(greaterArea.getCreateDate());
            greaterAreaAuditTrail.setAuditTrailUser(greaterArea.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_GREATER_AREA) {
            greaterAreaAuditTrail.setAuditTrailDate(greaterArea.getLastModifyDate());
            greaterAreaAuditTrail.setAuditTrailUser(greaterArea.getLastModifiedBy());
        }
        greaterAreaAuditTrail.setDeleted(greaterArea.isDeleted());
        return greaterAreaAuditTrail;
    }
    
}
