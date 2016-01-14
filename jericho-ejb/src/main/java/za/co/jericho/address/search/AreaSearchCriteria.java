package za.co.jericho.address.search;

import za.co.jericho.common.search.AbstractSearchCriteria;

public class AreaSearchCriteria extends AbstractSearchCriteria{
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getName() != null) {
            stringBuilder.append("|Name: ");
            stringBuilder.append(getName());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}