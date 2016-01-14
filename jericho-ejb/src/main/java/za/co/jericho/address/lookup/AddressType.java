package za.co.jericho.address.lookup;

public enum AddressType {
    
    STREET_ADDRESS("Street"),
    POSTAL("Postal");
    
    private String value;
    
    AddressType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}