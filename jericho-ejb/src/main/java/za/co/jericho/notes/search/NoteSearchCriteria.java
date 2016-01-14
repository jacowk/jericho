package za.co.jericho.notes.search;

import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.propertyflip.search.PropertyFlipSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-10
 */
public class NoteSearchCriteria extends AbstractSearchCriteria {
    
    private String description;
    private PropertyFlipSearchCriteria propertyFlipSearchCriteria;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyFlipSearchCriteria getPropertyFlipSearchCriteria() {
        return propertyFlipSearchCriteria;
    }

    public void setPropertyFlipSearchCriteria(PropertyFlipSearchCriteria 
        propertyFlipSearchCriteria) {
        this.propertyFlipSearchCriteria = propertyFlipSearchCriteria;
    }
    
}
