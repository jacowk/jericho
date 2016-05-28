package za.co.jericho.propertyflip.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.exception.EntityValidationException;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-18
 */
@Entity
@Table(name="property_flip_audit_trail", schema = "jericho")
public class PropertyFlipAuditTrail extends AbstractAuditTrailEntity {

    @Column(name = "reference_number")
    private Long referenceNumber;
    @Column(name = "title_deed_number")
    private String titleDeedNumber;
    @Column(name = "case_number")
    private String caseNumber;

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTitleDeedNumber() {
        return titleDeedNumber;
    }

    public void setTitleDeedNumber(String titleDeedNumber) {
        this.titleDeedNumber = titleDeedNumber;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }
    
    @Override
    public void validate() {
        if (getEntityId() == null) {
            throw new EntityValidationException("PropertyFlipAuditTrail Entity ID must be provided");
        }
    }
    
}
