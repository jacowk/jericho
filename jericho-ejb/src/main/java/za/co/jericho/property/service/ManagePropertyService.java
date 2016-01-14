package za.co.jericho.property.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.property.domain.Property;
import za.co.jericho.property.search.PropertySearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Remote
public interface ManagePropertyService<T> {
    
    public Property addProperty(Property property);

    public Property updateProperty(Property property);

    public Property markPropertyDeleted(Property property);

    public List searchProperties(PropertySearchCriteria propertySearchCriteria);
    
    public Property findProperty(Object id);
    
    public List<Property> findAllProperties();
    
}
