package za.co.jericho.address.service;

import za.co.jericho.address.domain.Address;
import za.co.jericho.address.domain.Address;
import za.co.jericho.address.domain.AddressAuditTrail;
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
public class AddressAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Address address = null;
        if (entity instanceof Address) {
            address = (Address) entity;
        }
        else {
            return null;
        }
        AddressAuditTrail addressAuditTrail = new AddressAuditTrail();
        addressAuditTrail.setEntityId(address.getId());
        addressAuditTrail.setAddressLine1(address.getAddressLine1());
        addressAuditTrail.setAddressLine2(address.getAddressLine2());
        addressAuditTrail.setAddressLine3(address.getAddressLine3());
        addressAuditTrail.setAddressLine4(address.getAddressLine4());
        addressAuditTrail.setAddressLine5(address.getAddressLine5());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        addressAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_ADDRESS) {
            addressAuditTrail.setAuditTrailDate(address.getCreateDate());
            addressAuditTrail.setAuditTrailUser(address.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_ADDRESS) {
            addressAuditTrail.setAuditTrailDate(address.getLastModifyDate());
            addressAuditTrail.setAuditTrailUser(address.getLastModifiedBy());
        }
        addressAuditTrail.setDeleted(address.isDeleted());
        return addressAuditTrail;
    }
    
}
