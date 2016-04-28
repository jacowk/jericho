package za.co.jericho.client.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.exception.EntityValidationException;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-04-25
 */
@Entity
@Table(name="seller_audit_trail", schema = "jericho")
public class SellerAuditTrail extends AbstractAuditTrailEntity {

    private Long propertyFlipId;
    private Long contactId;

    public Long getPropertyFlipId() {
        return propertyFlipId;
    }

    public void setPropertyFlipId(Long propertyFlipId) {
        this.propertyFlipId = propertyFlipId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
    
    @Override
    public void validate() {
        if (getPropertyFlipId() == null || getPropertyFlipId() < 1) {
            throw new EntityValidationException("SellerAuditTrail property flip id must be provided");
        }
        if (getContactId() == null || getContactId() < 1) {
            throw new EntityValidationException("SellerAuditTrail contact id must be provided");
        }
    }
    
}
