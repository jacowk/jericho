package za.co.jericho.bank.domain;

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
 * Date: 2015-11-28
 */
@Entity
@Table(name="bank_audit_trail", schema = "jericho")
public class BankAuditTrail extends AbstractAuditTrailEntity {
    
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type_id")
    private short typeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getTypeId() {
        return typeId;
    }

    public void setTypeId(short typeId) {
        this.typeId = typeId;
    }
    
    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getEntityId() == null) {
            throw new EntityValidationException("BankAuditTrail Entity ID must be provided");
        }
        if (getName() == null || stringValidator.isNullOrEmpty(getName())) {
            throw new EntityValidationException("BankAuditTrail Name must be provided");
        }
    }
    
}
