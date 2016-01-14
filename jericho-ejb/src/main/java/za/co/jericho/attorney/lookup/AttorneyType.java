package za.co.jericho.attorney.lookup;

public enum AttorneyType {
    
    TRANSFER_ATTORNEY(1, "Transfer Attorney"),
    BOND_REGISTERING_ATTORNEY(2, "Bond Registering Attorney"),
    BOND_CANCELATION_ATTORNEY(3, "Bond Cancelation Attorney"),
    PURCHASERS_BOND_ATTORNEY(4, "Purchaser's Bond Attorney");
    
    private long id;
    private String value;
    
    AttorneyType(long id, String value) {
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