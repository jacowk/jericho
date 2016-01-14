package za.co.jericho.attorney.service;

import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.attorney.domain.Attorney;
import za.co.jericho.attorney.search.AttorneySearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-08
 */
@Remote
public interface ManageAttorneyService {
 
    public Attorney addAttorney(Attorney attorney);
    
    public Attorney updateAttorney(Attorney attorney);
    
    public Attorney markAttorneyDeleted(Attorney attorney);
    
    public Collection<Attorney> searchAttorneys(AttorneySearchCriteria attorneySearchCriteria);
    
    public Attorney findAttorney(Object id);
    
    public Collection<Attorney> findAllAttorneys();
    
}
