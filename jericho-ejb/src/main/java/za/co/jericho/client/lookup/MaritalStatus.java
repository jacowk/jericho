package za.co.jericho.client.lookup;

public enum MaritalStatus {
    
    SINGLE_NEVER_MARRIED(1, "Single Never Married"),
    SINGLE_DIVORCED(2, "Single Divorced"),
    MARRIED(3, "Married"),
    WIDOWED(4, "Widowed");
    
    private long id;
    private String value;
    
    MaritalStatus(long id, String value) {
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