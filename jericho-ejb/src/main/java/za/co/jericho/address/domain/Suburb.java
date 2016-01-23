package za.co.jericho.address.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.exception.NonNumericStringException;
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
    private String boxCode; /* String because these codes are zero padded */
    @Column(name = "street_code")
    private String streetCode; /* String because these codes are zero padded */
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
        if (stringValidator.isNullOrEmpty(getBoxCode()) && stringValidator
            .isNullOrEmpty(getStreetCode())) {
            throw new EntityValidationException("SuburbAuditTrail Box code or Street code must be provided");
        }
        if (!stringValidator.isNullOrEmpty(getBoxCode())) {
            if (!stringValidator.isNumeric(getBoxCode())) {
                StringBuilder output = new StringBuilder();
                output.append("The Box Code ");
                output.append(getBoxCode());
                output.append(", is not a number");
                throw new NonNumericStringException(output.toString());
            }
        }
        if (!stringValidator.isNullOrEmpty(getStreetCode())) {
            if (!stringValidator.isNumeric(getStreetCode())) {
                StringBuilder output = new StringBuilder();
                output.append("The Street Code ");
                output.append(getStreetCode());
                output.append(", is not a number");
                throw new NonNumericStringException(output.toString());
            }
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
        if (getBoxCode() != null) {
            stringBuilder.append("\nBoxCode: ");
            stringBuilder.append(getBoxCode());
        }
        if (getStreetCode() != null) {
            stringBuilder.append("\nStreetCode: ");
            stringBuilder.append(getStreetCode());
        }
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}