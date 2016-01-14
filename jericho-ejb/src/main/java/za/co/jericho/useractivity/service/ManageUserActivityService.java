package za.co.jericho.useractivity.service;

import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.useractivity.domain.UserActivity;
import za.co.jericho.useractivity.search.UserActivitySearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-28
 */
@Remote
public interface ManageUserActivityService {
    
    public UserActivity addUserActivity(UserActivity userActivity);
    
    public Collection<UserActivity> searchUserActivities(UserActivitySearchCriteria userActivitySearchCriteria);
    
}
