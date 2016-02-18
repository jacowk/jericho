package za.co.jericho.property.domain;

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
@Table(name="property_audit_trail", schema = "jericho")
public class PropertyAuditTrail extends AbstractAuditTrailEntity {

    @Column(name = "erf_no")
    private Integer erfNumber;
    @Column(name = "portion_no")
    private Integer portionNumber;
    @Column(name = "property_size")
    private Integer propertySize;

    public Integer getErfNumber() {
        return erfNumber;
    }

    public void setErfNumber(Integer erfNumber) {
        this.erfNumber = erfNumber;
    }

    public Integer getPortionNumber() {
        return portionNumber;
    }

    public void setPortionNumber(Integer portionNumber) {
        this.portionNumber = portionNumber;
    }

    public Integer getPropertySize() {
        return propertySize;
    }

    public void setPropertySize(Integer propertySize) {
        this.propertySize = propertySize;
    }
    
    @Override
    public void validate() {
        if (getEntityId() == null) {
            throw new EntityValidationException("PropertyAuditTrail Entity ID must be provided");
        }
    }
    
}
