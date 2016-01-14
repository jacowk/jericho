package za.co.jericho.estateagent.lookup;

public enum EstateAgentType {
    
    PURCHASING_AGENT(1, "Purchasing Agent"),
    SELLING_AGENT(2, "Selling Agent");
    
    private long id;
    private String value;
    
    EstateAgentType(long id, String value)
    {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}