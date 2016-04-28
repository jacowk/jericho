package za.co.jericho.contact.lookup;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
public enum MaritalStatusConstants {
    
    SINGLE(1, "Single"),
    MARRIED(2, "Married"),
    DIVORCED(3, "Divorced"),
    WIDOWED(4, "Widowed"),
    OTHER(5, "Other");
    
    private long id;
    private String description;
    
    MaritalStatusConstants(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}