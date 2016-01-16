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
@Table(name="greater_area_audit_trail", schema = "jericho")
public class GreaterAreaAuditTrail extends AbstractAuditTrailEntity {

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getEntityId() == null) {
            throw new EntityValidationException("GreaterAreaAuditTrail Entity ID must be provided");
        }
        if (getName() == null || stringValidator.isNullOrEmpty(getName())) {
            throw new EntityValidationException("GreaterAreaAuditTrail Name must be provided");
        }
    }
    
}
