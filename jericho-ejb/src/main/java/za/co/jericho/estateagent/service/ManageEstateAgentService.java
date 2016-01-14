package za.co.jericho.estateagent.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.estateagent.domain.EstateAgent;
import za.co.jericho.estateagent.search.EstateAgentSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-09
 */
@Remote
public interface ManageEstateAgentService {
    
    public EstateAgent addEstateAgent(EstateAgent estateAgent);
    
    public EstateAgent updateEstateAgent(EstateAgent estateAgent);
    
    public EstateAgent markEstateAgentDeleted(EstateAgent estateAgent);
    
    public List<EstateAgent> searchEstateAgents(EstateAgentSearchCriteria 
        estateAgentSearchCriteria);
    
    public EstateAgent findEstateAgent(Object id);
    
    public List<EstateAgent> findAllEstateAgents();
    
}
