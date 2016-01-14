package za.co.jericho.property.search;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-21
 */
public enum PropertySearchBy {
    
    SELECT_CRITERIA("Select Criteria"),
    ADDRESS("Address"),
    ERF_NUMBER("Erf Number"),
    REFERENCE_NUMBER("Reference Number"),
    TITLE_DEED_NUMBER("Title Deed Number"),
    CASE_NUMBER("Case Number");
    
    private String value;
    
    PropertySearchBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
