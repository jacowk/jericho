package za.co.jericho.attorney.service;

import za.co.jericho.attorney.domain.Attorney;
import za.co.jericho.attorney.domain.AttorneyAuditTrail;
import za.co.jericho.audittrail.service.AbstractAuditTrailService;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-04
 */
public class AttorneyAuditTrailService extends AbstractAuditTrailService {
 
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Attorney attorney = null;
        if (entity instanceof Attorney) {
            attorney = (Attorney) entity;
        }
        else {
            return null;
        }
        AttorneyAuditTrail attorneyAuditTrail = new AttorneyAuditTrail();
        attorneyAuditTrail.setEntityId(attorney.getId());
        attorneyAuditTrail.setName(attorney.getName());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        attorneyAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_ATTORNEY) {
            attorneyAuditTrail.setAuditTrailDate(attorney.getCreateDate());
            attorneyAuditTrail.setAuditTrailUser(attorney.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_ATTORNEY) {
            attorneyAuditTrail.setAuditTrailDate(attorney.getLastModifyDate());
            attorneyAuditTrail.setAuditTrailUser(attorney.getLastModifiedBy());
        }
        attorneyAuditTrail.setDeleted(attorney.isDeleted());
        return attorneyAuditTrail;
    }
    
}
