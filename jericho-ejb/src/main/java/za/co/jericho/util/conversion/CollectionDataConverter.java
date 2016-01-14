package za.co.jericho.util.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-09
 */
public class CollectionDataConverter implements CollectionConverter {
    
    @Override
    public List convertCollectionToList(Collection objects) {
        List list = new ArrayList();
        for (Object object: objects) {
            list.add(object);
        }
        return list;
    }
    
    @Override
    public Collection convertListToCollection(List list) {
        Collection collection = new ArrayList();
        collection.addAll(list);
        return collection;
    }
    
}
