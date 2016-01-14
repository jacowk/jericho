package za.co.jericho.address.search;

import za.co.jericho.address.domain.*;
import za.co.jericho.common.search.AbstractSearchCriteria;

public class SuburbSearchCriteria extends AbstractSearchCriteria{

    private String name;
    private String boxCode;
    private String streetCode;
    private Area area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getStreetCode() {
        return streetCode;
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
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
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