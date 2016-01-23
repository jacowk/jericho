package za.co.jericho.address.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Entity
@Table(name="area")
@NamedQueries({
    @NamedQuery(name = "findAreaByName", query = "SELECT a FROM Area a WHERE a.name like :name")
})
public class Area extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Suburb> suburbs;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Suburb> getSuburbs() {
        return suburbs;
    }

    public void setSuburb(List<Suburb> suburbs) {
        this.suburbs = suburbs;
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
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getName() != null) {
            stringBuilder.append("\nName: ");
            stringBuilder.append(getName());
        }
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}