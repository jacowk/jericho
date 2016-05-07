package za.co.jericho.property.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @OneToOne(mappedBy="property")
    private PropertyFlip propertyFlip = new PropertyFlip();

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

    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {
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
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getErfNumber() != null) {
            stringBuilder.append("\nErf Number: ");
            stringBuilder.append(getErfNumber());
        }
        if (getPortionNumber() != null) {
            stringBuilder.append("\nPortion Number: ");
            stringBuilder.append(getPortionNumber());
        }
        if (getPropertySize() != null) {
            stringBuilder.append("\nPropertySize: ");
            stringBuilder.append(getPropertySize());
        }
        if (getAddress() != null) {
            stringBuilder.append("\nAddress: ");
            stringBuilder.append(getAddress().getId());
        }
        if (getPropertyFlip() != null) {
            stringBuilder.append("\nPropertyFlip: ");
//            stringBuilder.append(getPropertyFlip().getId());
        }
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}