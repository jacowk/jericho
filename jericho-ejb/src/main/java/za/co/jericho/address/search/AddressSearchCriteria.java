package za.co.jericho.address.search;

import za.co.jericho.address.domain.Area;
import za.co.jericho.address.domain.GreaterArea;
import za.co.jericho.address.domain.Suburb;
import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.property.domain.Property;

public class AddressSearchCriteria extends AbstractSearchCriteria{

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private Property property;
    private Suburb suburb;
    private Area area;
    private GreaterArea greaterArea;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public GreaterArea getGreaterArea() {
        return greaterArea;
    }

    public void setGreaterArea(GreaterArea greaterArea) {
        this.greaterArea = greaterArea;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
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
        if (getSuburb() != null) {
            stringBuilder.append("|Area: ");
            stringBuilder.append(getArea().getId());
            stringBuilder.append(",");
            stringBuilder.append(getArea().getName());
        }
        if (getSuburb() != null) {
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