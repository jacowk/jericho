package za.co.jericho.audit.search;

import java.util.Date;
import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.security.domain.User;

public class AuditActivitySearchCriteria extends AbstractSearchCriteria {
    
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
    
}