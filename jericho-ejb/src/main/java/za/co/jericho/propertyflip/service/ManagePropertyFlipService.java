package za.co.jericho.propertyflip.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.propertyflip.domain.Milestone;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.propertyflip.search.MilestoneSearchCriteria;
import za.co.jericho.propertyflip.search.PropertyFlipSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Remote
public interface ManagePropertyFlipService {
    
    public PropertyFlip addPropertyFlip(PropertyFlip propertyFlip);

    public PropertyFlip updatePropertyFlip(PropertyFlip propertyFlip);

    public PropertyFlip markPropertyFlipDeleted(PropertyFlip propertyFlip);

    public List<PropertyFlip> searchPropertyFlips(PropertyFlipSearchCriteria 
        propertyFlipSearchCriteria);
    
    public PropertyFlip findPropertyFlip(Object id);
    
    public List<PropertyFlip> findAllPropertyFlips();

    public Milestone addMilestone(Milestone milestone);

    public Milestone updateMilestone(Milestone milestone);

    public Milestone markMilestoneDeleted(Milestone milestone);

    public List searchMilestones(MilestoneSearchCriteria milestoneSearchCriteria);
    
    public Milestone findMilestone(Object id);
    
    public List<Milestone> findAllMilestones();
    
}
