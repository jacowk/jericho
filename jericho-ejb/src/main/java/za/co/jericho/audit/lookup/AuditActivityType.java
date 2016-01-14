package za.co.jericho.audit.lookup;

public enum AuditActivityType {
    
    LOGIN("Login"),
    ADD("Add"), 
    UPDATE("Update"), 
    DELETE("Delete"), 
    SEARCH("Search"),
    LOGOUT("Logout");
    
    private String value;
    
    AuditActivityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}