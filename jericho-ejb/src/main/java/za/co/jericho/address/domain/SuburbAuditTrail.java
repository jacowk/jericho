package za.co.jericho.address.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-16
 */
@Entity
@Table(name="suburb_audit_trail", schema = "jericho")
public class SuburbAuditTrail extends AbstractAuditTrailEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "box_code")
    private String boxCode;
    @Column(name = "street_code")
    private String streetCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }
    
    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getEntityId() == null) {
            throw new EntityValidationException("SuburbAuditTrail Entity ID must be provided");
        }
        if (getName() == null || stringValidator.isNullOrEmpty(getName())) {
            throw new EntityValidationException("SuburbAuditTrail Name must be provided");
        }
        if ((getBoxCode() == null || stringValidator.isNullOrEmpty(getBoxCode())) &&
            (getStreetCode() == null || stringValidator.isNullOrEmpty(getStreetCode()))) {
            throw new EntityValidationException("SuburbAuditTrail Box code or Street code must be provided");
        }
    }
    
}
