package za.co.jericho.audit.lookup;

/**
 *
 * @author user
 */
public enum EntityName {
    
    ACCOUNT("Account"),
    ADDRESS("Address"),
    SUBURB("Suburb"),
    AREA("Area"),
    GREATER_AREA("Greater Area"),
    ATTORNEY("Attorney"),
    BANK("Bank"),
    CLIENT("Client"),
    CONTACT("Contact"),
    CONTRACTOR("Contractor"),
    DIARY("Diary"),
    DIARY_ITEM("Diary Item"),
    FOLLOWUP_ITEM("Followup Item"),
    DOCUMENT("Document"),
    ESTATE_AGENT("Estate Agent"),
    NOTE("Note"),
    PROPERTY("Property"),
    PROPERTY_FLIP("Property Flip"),
    PURCHASER("Purchaser"),
    MILESTONE("Milestone"),
    SECURITY_USER("User"),
    SECURITY_PERMISSION("Permission"),
    SECURITY_ROLE("Role"),
    SELLER("Seller"),
    TRANSACTION("Transaction"),
    TRANSACTION_ITEM("Transaction Item"),
    REPORT("Report");
    
    private String value;
    
    EntityName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
