package za.co.jericho.security.domain;

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
@Table(name="permission_audit_trail", schema = "jericho")
public class PermissionAuditTrail extends AbstractAuditTrailEntity {
    
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "service_name")
    private String serviceName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getName() == null || stringValidator.isNullOrEmpty(getName())) {
            throw new EntityValidationException("Permission name must be provided");
        }
        if (getServiceName() == null || stringValidator.isNullOrEmpty(getServiceName())) {
            throw new EntityValidationException("Permission service name must be provided");
        }
    }
}
