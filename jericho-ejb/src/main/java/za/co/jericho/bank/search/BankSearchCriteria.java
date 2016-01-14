package za.co.jericho.bank.search;

import za.co.jericho.common.search.AbstractSearchCriteria;

public class BankSearchCriteria extends AbstractSearchCriteria{

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}