package za.co.jericho.audittrail.service;

import za.co.jericho.address.domain.Area;
import za.co.jericho.address.domain.AreaAuditTrail;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-16
 */
public class AreaAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Area area = null;
        if (entity instanceof Area) {
            area = (Area) entity;
        }
        else {
            return null;
        }
        AreaAuditTrail areaAuditTrail = new AreaAuditTrail();
        areaAuditTrail.setEntityId(area.getId());
        areaAuditTrail.setName(area.getName());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        areaAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_AREA) {
            areaAuditTrail.setAuditTrailDate(area.getCreateDate());
            areaAuditTrail.setAuditTrailUser(area.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_AREA) {
            areaAuditTrail.setAuditTrailDate(area.getLastModifyDate());
            areaAuditTrail.setAuditTrailUser(area.getLastModifiedBy());
        }
        areaAuditTrail.setDeleted(area.isDeleted());
        return areaAuditTrail;
    }
    
}
