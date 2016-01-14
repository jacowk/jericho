package za.co.jericho.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Entity
@Table(name="permission", schema="jericho")
public class Permission extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "service_name")
    private String serviceName;
    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.REFRESH)
    private Collection<Role> roles = new ArrayList<Role>();

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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    
    public void addRole(Role role) {
        this.roles.add(role);
    }
    
    public void addRoleCollection(Collection<Role> roles) {
        for (Role role: roles) {
            this.roles.add(role);
        }
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
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getName() != null) {
            stringBuilder.append("\nName: ");
            stringBuilder.append(getName());
        }
        if (getServiceName() != null) {
            stringBuilder.append("\nService Name: ");
            stringBuilder.append(getServiceName());
        }
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}