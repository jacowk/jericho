package za.co.jericho.useractivity.search;

import java.util.Date;
import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-28
 */
public class UserActivitySearchCriteria extends AbstractSearchCriteria {
    
    private User user;
    private Date activityFromDate;
    private Date activityToDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getActivityFromDate() {
        return activityFromDate;
    }

    public void setActivityFromDate(Date activityFromDate) {
        this.activityFromDate = activityFromDate;
    }

    public Date getActivityToDate() {
        return activityToDate;
    }

    public void setActivityToDate(Date activityToDate) {
        this.activityToDate = activityToDate;
    }
    
    public void validate() {
        if (getActivityFromDate() == null) {
            throw new ServiceBeanException("User Activity from date not provided for search");
        }
        if (getActivityToDate() == null) {
            throw new ServiceBeanException("User Activity to date not provided for search");
        }
        if (getUser() == null) {
            throw new ServiceBeanException("User Activity User not specified for search");
        }
    }
    
}
