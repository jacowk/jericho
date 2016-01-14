package za.co.jericho.account.search;

import za.co.jericho.common.search.AbstractSearchCriteria;

public class AccountSearchCriteria extends AbstractSearchCriteria {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}