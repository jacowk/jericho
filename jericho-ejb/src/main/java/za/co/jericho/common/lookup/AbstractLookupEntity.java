package za.co.jericho.common.lookup;

import za.co.jericho.common.domain.*;

public abstract class AbstractLookupEntity extends AbstractEntity {

    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}