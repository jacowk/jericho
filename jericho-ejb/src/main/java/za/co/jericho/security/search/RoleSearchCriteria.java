package za.co.jericho.security.search;

import za.co.jericho.common.search.AbstractSearchCriteria;

public class RoleSearchCriteria extends AbstractSearchCriteria{
    
    private String name = "";
    private boolean deleted = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}