package za.co.jericho.util.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-09
 */
public interface CollectionConverter {
    
    public List convertCollectionToList(Collection objects);
    
    public Collection convertListToCollection(List list);
    
}
