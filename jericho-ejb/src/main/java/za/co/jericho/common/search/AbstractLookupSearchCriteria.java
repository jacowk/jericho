package za.co.jericho.common.search;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-14
 */
public abstract class AbstractLookupSearchCriteria extends AbstractSearchCriteria {
    
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
