package za.co.jericho.client.service;

import za.co.jericho.audittrail.service.AbstractAuditTrailService;
import za.co.jericho.client.domain.Seller;
import za.co.jericho.client.domain.SellerAuditTrail;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-04-25
 */
public class SellerAuditTrailService extends AbstractAuditTrailService {

    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Seller seller = null;
        if (entity instanceof Seller) {
            seller = (Seller) entity;
        }
        else {
            return null;
        }
        SellerAuditTrail sellerAuditTrail = new SellerAuditTrail();
        sellerAuditTrail.setEntityId(seller.getId());
        if (seller.getPropertyFlip() != null) {
            sellerAuditTrail.setPropertyFlipId(seller.getPropertyFlip().getId());
        }
        if (seller.getContact() != null) {
            sellerAuditTrail.setContactId(seller.getContact().getId());
        }
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        sellerAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_SELLER) {
            sellerAuditTrail.setAuditTrailDate(seller.getCreateDate());
            sellerAuditTrail.setAuditTrailUser(seller.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_SELLER) {
            sellerAuditTrail.setAuditTrailDate(seller.getLastModifyDate());
            sellerAuditTrail.setAuditTrailUser(seller.getLastModifiedBy());
        }
        sellerAuditTrail.setDeleted(seller.isDeleted());
        return sellerAuditTrail;
    }
    
}
