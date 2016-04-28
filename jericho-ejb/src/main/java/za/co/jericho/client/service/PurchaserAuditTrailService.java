package za.co.jericho.client.service;

import za.co.jericho.audittrail.service.AbstractAuditTrailService;
import za.co.jericho.client.domain.Purchaser;
import za.co.jericho.client.domain.PurchaserAuditTrail;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-04-25
 */
public class PurchaserAuditTrailService extends AbstractAuditTrailService {

    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Purchaser purchaser = null;
        if (entity instanceof Purchaser) {
            purchaser = (Purchaser) entity;
        }
        else {
            return null;
        }
        PurchaserAuditTrail purchaserAuditTrail = new PurchaserAuditTrail();
        purchaserAuditTrail.setEntityId(purchaser.getId());
        if (purchaser.getPropertyFlip() != null) {
            purchaserAuditTrail.setPropertyFlipId(purchaser.getPropertyFlip().getId());
        }
        if (purchaser.getContact() != null) {
            purchaserAuditTrail.setContactId(purchaser.getContact().getId());
        }
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        purchaserAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_PURCHASER) {
            purchaserAuditTrail.setAuditTrailDate(purchaser.getCreateDate());
            purchaserAuditTrail.setAuditTrailUser(purchaser.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_PURCHASER) {
            purchaserAuditTrail.setAuditTrailDate(purchaser.getLastModifyDate());
            purchaserAuditTrail.setAuditTrailUser(purchaser.getLastModifiedBy());
        }
        purchaserAuditTrail.setDeleted(purchaser.isDeleted());
        return purchaserAuditTrail;
    }
    
}
