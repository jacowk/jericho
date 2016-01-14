package za.co.jericho.address.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import za.co.jericho.common.domain.*;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.property.domain.*;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Entity
@Table(name="address")
@NamedQueries({
    @NamedQuery(name = "findAddressByProperty", query = "SELECT a FROM Address a WHERE a.property = :property"),
    @NamedQuery(name = "findAddressBySuburb", query = "SELECT a FROM Address a WHERE a.suburb = :suburb"),
    @NamedQuery(name = "findAddressByGreaterArea", query = "SELECT a FROM Address a WHERE a.greaterArea = :greaterArea")
})
public class Address extends AbstractEntity {

    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Column(name = "address_line_3")
    private String addressLine3;
    @Column(name = "address_line_4")
    private String addressLine4;
    @Column(name = "address_line_5")
    private String addressLine5;
    @OneToOne(mappedBy="address")
    private Property property;
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "suburb_id")
    private Suburb suburb;
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "greater_area_id")
    private GreaterArea greaterArea;

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return this.addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return this.addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }

    public GreaterArea getGreaterArea() {
        return greaterArea;
    }

    public void setGreaterArea(GreaterArea greaterArea) {
        this.greaterArea = greaterArea;
    }

    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (stringValidator.isNullOrEmpty(getAddressLine1()) &&
            stringValidator.isNullOrEmpty(getAddressLine2()) &&
            stringValidator.isNullOrEmpty(getAddressLine3()) &&
            stringValidator.isNullOrEmpty(getAddressLine4()) &&
            stringValidator.isNullOrEmpty(getAddressLine5())) {
            throw new EntityValidationException("At least one address line must be provided");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("|ID: ");
            stringBuilder.append(getId());
        }
        if (getAddressLine1() != null) {
            stringBuilder.append("|AddressLine1: ");
            stringBuilder.append(getAddressLine1());
        }
        if (getAddressLine2() != null) {
            stringBuilder.append("|AddressLine2: ");
            stringBuilder.append(getAddressLine2());
        }
        if (getAddressLine3() != null) {
            stringBuilder.append("|AddressLine3: ");
            stringBuilder.append(getAddressLine3());
        }
        if (getAddressLine4() != null) {
            stringBuilder.append("|AddressLine4: ");
            stringBuilder.append(getAddressLine4());
        }
        if (getAddressLine5() != null) {
            stringBuilder.append("|AddressLine5: ");
            stringBuilder.append(getAddressLine5());
        }
        if (getProperty() != null) {
            stringBuilder.append("|Property: ");
            stringBuilder.append(getProperty().getId());
        }
        if (getSuburb() != null) {
            stringBuilder.append("|Suburb: ");
            stringBuilder.append(getSuburb().getId());
            stringBuilder.append(",");
            stringBuilder.append(getSuburb().getName());
        }
        if (getGreaterArea() != null) {
            stringBuilder.append("|GreaterArea: ");
            stringBuilder.append(getGreaterArea().getId());
            stringBuilder.append(",");
            stringBuilder.append(getGreaterArea().getName());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}