package za.co.jericho.security.search;

import za.co.jericho.common.search.AbstractSearchCriteria;

public class PermissionSearchCriteria extends AbstractSearchCriteria {
    
    private String name;
    private String serviceName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}