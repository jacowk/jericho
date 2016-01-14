package za.co.jericho.audittrail.service;

import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.PermissionAuditTrail;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-04
 */
public class PermissionAuditTrailService extends AbstractAuditTrailService {

    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Permission permission = null;
        if (entity instanceof Permission) {
            permission = (Permission) entity;
        }
        else {
            return null;
        }
        PermissionAuditTrail permissionAuditTrail = new PermissionAuditTrail();
        permissionAuditTrail.setEntityId(permission.getId());
        permissionAuditTrail.setName(permission.getName());
        permissionAuditTrail.setServiceName(permission.getServiceName());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        permissionAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_PERMISSION) {
            permissionAuditTrail.setAuditTrailDate(permission.getCreateDate());
            permissionAuditTrail.setAuditTrailUser(permission.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_PERMISSION) {
            permissionAuditTrail.setAuditTrailDate(permission.getLastModifyDate());
            permissionAuditTrail.setAuditTrailUser(permission.getLastModifiedBy());
        }
        permissionAuditTrail.setDeleted(permission.isDeleted());
        return permissionAuditTrail;
    }
}
