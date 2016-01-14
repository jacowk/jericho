package za.co.jericho.address.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import za.co.jericho.common.domain.*;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Entity
@Table(name="suburb")
@NamedQueries({
    @NamedQuery(name = "findSuburbByName", query = "SELECT s FROM Suburb s WHERE s.name like :name"),
    @NamedQuery(name = "findSuburbByBoxCode", query = "SELECT s FROM Suburb s WHERE s.boxCode = :boxCode"),
    @NamedQuery(name = "findSuburbByStreetCode", query = "SELECT s FROM Suburb s WHERE s.streetCode = :streetCode")
})
public class Suburb extends AbstractEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "box_code")
    private String boxCode;
    @Column(name = "street_code")
    private String streetCode;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="area_id")
    private Area area;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoxCode() {
        return this.boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getStreetCode() {
        return this.streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
        if (getBoxCode() != null) {
            stringBuilder.append("|BoxCode: ");
            stringBuilder.append(getBoxCode());
        }
        if (getStreetCode() != null) {
            stringBuilder.append("|StreetCode: ");
            stringBuilder.append(getStreetCode());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}