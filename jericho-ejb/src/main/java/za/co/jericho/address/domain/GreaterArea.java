package za.co.jericho.address.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import za.co.jericho.common.domain.*;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Entity
@Table(name="greater_area")
@NamedQueries({
    @NamedQuery(name = "findGreaterAreaByName", query = "SELECT ga FROM GreaterArea ga WHERE ga.name like :name")
})
public class GreaterArea extends AbstractEntity {

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
        if (stringValidator.isNullOrEmpty(getName())) {
            throw new EntityValidationException("Name not provided");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("|ID: ");
            stringBuilder.append(getId());
        }
        if (getName() != null) {
            stringBuilder.append("|Name: ");
            stringBuilder.append(getName());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}