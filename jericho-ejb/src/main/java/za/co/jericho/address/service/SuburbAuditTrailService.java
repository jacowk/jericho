package za.co.jericho.address.service;

import za.co.jericho.address.domain.Suburb;
import za.co.jericho.address.domain.SuburbAuditTrail;
import za.co.jericho.audittrail.service.AbstractAuditTrailService;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-16
 */
public class SuburbAuditTrailService extends AbstractAuditTrailService {

    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Suburb suburb = null;
        if (entity instanceof Suburb) {
            suburb = (Suburb) entity;
        }
        else {
            return null;
        }
        SuburbAuditTrail suburbAuditTrail = new SuburbAuditTrail();
        suburbAuditTrail.setEntityId(suburb.getId());
        suburbAuditTrail.setName(suburb.getName());
        suburbAuditTrail.setBoxCode(suburb.getBoxCode());
        suburbAuditTrail.setStreetCode(suburb.getStreetCode());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        suburbAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_SUBURB) {
            suburbAuditTrail.setAuditTrailDate(suburb.getCreateDate());
            suburbAuditTrail.setAuditTrailUser(suburb.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_SUBURB) {
            suburbAuditTrail.setAuditTrailDate(suburb.getLastModifyDate());
            suburbAuditTrail.setAuditTrailUser(suburb.getLastModifiedBy());
        }
        suburbAuditTrail.setDeleted(suburb.isDeleted());
        return suburbAuditTrail;
    }
    
}
