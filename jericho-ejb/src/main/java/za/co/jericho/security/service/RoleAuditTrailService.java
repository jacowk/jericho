package za.co.jericho.security.service;

import za.co.jericho.audittrail.service.AbstractAuditTrailService;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.domain.RoleAuditTrail;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-31
 */
public class RoleAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Role role = null;
        if (entity instanceof Role) {
            role = (Role) entity;
        }
        else {
            return null;
        }
        RoleAuditTrail roleAuditTrail = new RoleAuditTrail();
        roleAuditTrail.setEntityId(role.getId());
        roleAuditTrail.setName(role.getName());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        roleAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_ROLE) {
            roleAuditTrail.setAuditTrailDate(role.getCreateDate());
            roleAuditTrail.setAuditTrailUser(role.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_ROLE) {
            roleAuditTrail.setAuditTrailDate(role.getLastModifyDate());
            roleAuditTrail.setAuditTrailUser(role.getLastModifiedBy());
        }
        roleAuditTrail.setDeleted(role.isDeleted());
        return roleAuditTrail;
    }
    
}
