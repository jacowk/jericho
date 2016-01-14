package za.co.jericho.security;

/**
 *
 * @author user
 */
public enum PermissionGroups {
    
    JERICHO("jericho"),
    ADMIN("admin");
    
    private String value;
    
    PermissionGroups(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
