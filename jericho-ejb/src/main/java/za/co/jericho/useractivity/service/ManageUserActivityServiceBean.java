package za.co.jericho.useractivity.service;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.useractivity.domain.UserActivity;
import za.co.jericho.useractivity.search.UserActivitySearchCriteria;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-28
 */
@Stateless
@Remote(ManageUserActivityService.class)
public class ManageUserActivityServiceBean extends AbstractServiceBean
implements ManageUserActivityService {

    @Override
    public UserActivity addUserActivity(UserActivity userActivity) {
        Logger.getRootLogger().info("ManageUserActivityServiceBean: addUserActivity: " + 
            userActivity.getDescription());
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateUserActivityEntityBeforeCreate(userActivity)) {
            getEntityManager().persist(userActivity);
        }
        return userActivity;
    }

    @Override
    public Collection<UserActivity> searchUserActivities(UserActivitySearchCriteria 
        userActivitySearchCriteria) {
        Logger.getRootLogger().info("ManageUserActivityServiceBean: userActivitySearchCriteria");
        Collection<UserActivity> userActivities = new ArrayList<>();
        if (userActivitySearchCriteria != null) {
            userActivitySearchCriteria.validate();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT ua FROM UserActivity ua ");
            searchUsersStringBuilder.append("WHERE ua.createDate BETWEEN :activityToDate AND :activityToDate ");
            searchUsersStringBuilder.append("AND ua.createdBy = :activityUser ");
            userActivities = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("activityFromDate", userActivitySearchCriteria.getActivityFromDate())
                .setParameter("activityToDate", userActivitySearchCriteria.getActivityToDate())
                .setParameter("activityUser", userActivitySearchCriteria.getUser())
                .getResultList();
        }
        else {
            throw new ServiceBeanException("User Activity search criteria not provided");
        }
        return userActivities;
    }
    
}
