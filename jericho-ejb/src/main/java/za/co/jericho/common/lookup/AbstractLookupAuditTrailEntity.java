package za.co.jericho.common.lookup;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-04
 */
@MappedSuperclass
public abstract class AbstractLookupAuditTrailEntity extends AbstractAuditTrailEntity {
    
    @Column(name = "description", nullable = false)
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getDescription() == null || stringValidator.isNullOrEmpty(getDescription())) {
            throw new EntityValidationException("Description must be provided");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getDescription() != null) {
            stringBuilder.append("\nDescription: ");
            stringBuilder.append(getDescription());
        }
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }
    
}
