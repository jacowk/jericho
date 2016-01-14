package za.co.jericho.property.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import za.co.jericho.address.domain.Address;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Entity
@Table(name="property", schema="jericho")
@NamedQueries({
    @NamedQuery(name = "findPropertyByErf", query = "SELECT p FROM Property p WHERE p.erfNumber = :erf")
})
public class Property extends AbstractEntity {

    @Column(name = "erf_no")
    private Integer erfNumber;
    @Column(name = "portion_no")
    private Integer portionNumber;
    @Column(name = "property_size")
    private Integer propertySize;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address = new Address();
    @OneToMany(mappedBy = "property", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<PropertyFlip> propertyFlip = new ArrayList<>();

    public Integer getErfNumber() {
        return this.erfNumber;
    }

    public void setErfNumber(Integer erfNumber) {
        this.erfNumber = erfNumber;
    }

    public Integer getPortionNumber() {
        return portionNumber;
    }

    public void setPortionNumber(Integer portionNumber) {
        this.portionNumber = portionNumber;
    }

    public Integer getPropertySize() {
        return propertySize;
    }

    public void setPropertySize(Integer propertySize) {
        this.propertySize = propertySize;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<PropertyFlip> getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(List<PropertyFlip> propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getErfNumber() == null || stringValidator.isNullOrEmpty(getErfNumber().toString())) {
            throw new EntityValidationException("Erf number must be provided");
        }
        if (getAddress() == null) {
            throw new EntityValidationException("An address must be provided for the property");
        }
        address.validate();
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("|ID: ");
            stringBuilder.append(getId());
        }
        if (getErfNumber() != null) {
            stringBuilder.append("|Erf Number: ");
            stringBuilder.append(getErfNumber());
        }
        if (getPortionNumber() != null) {
            stringBuilder.append("|Portion Number: ");
            stringBuilder.append(getPortionNumber());
        }
        if (getPropertySize() != null) {
            stringBuilder.append("|PropertySize: ");
            stringBuilder.append(getPropertySize());
        }
        if (getAddress() != null) {
            stringBuilder.append("|Address: ");
            stringBuilder.append(getAddress().getId());
        }
        if (getPropertyFlip() != null) {
            stringBuilder.append("|PropertyFlip: ");
//            stringBuilder.append(getPropertyFlip().getId());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}