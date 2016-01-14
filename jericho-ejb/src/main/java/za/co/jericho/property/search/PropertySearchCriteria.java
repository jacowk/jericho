package za.co.jericho.property.search;

import java.util.ArrayList;
import java.util.List;
import za.co.jericho.common.search.AbstractSearchCriteria;

public class PropertySearchCriteria extends AbstractSearchCriteria {
    
    private final List<String> searchByList = new ArrayList<>();
    private String selectedSearchBy;
    private String searchValue;
    
    public PropertySearchCriteria() {
        
    }

    public List<String> getSearchByList() {
        if (searchByList == null || searchByList.size() < 1) {
            for (PropertySearchBy propertySearchBy: PropertySearchBy.values()) {
                searchByList.add(propertySearchBy.getValue());
            }
        }
        return searchByList;
    }

    public String getSelectedSearchBy() {
        return selectedSearchBy;
    }

    public void setSelectedSearchBy(String selectedSearchBy) {
        this.selectedSearchBy = selectedSearchBy;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getSelectedSearchBy() != null) {
            stringBuilder.append("|selectedSearchBy: ");
            stringBuilder.append(getSelectedSearchBy());
        }
        if (getSearchValue() != null) {
            stringBuilder.append("|searchValue: ");
            stringBuilder.append(getSearchValue());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }
    
}