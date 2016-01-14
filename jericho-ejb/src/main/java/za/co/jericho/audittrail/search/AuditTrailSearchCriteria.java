package za.co.jericho.audittrail.search;

import java.util.Date;
import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-28
 */
public class AuditTrailSearchCriteria extends AbstractSearchCriteria {
    
    private User user;
    private Date auditTrailFromDate;
    private Date auditTrailToDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAuditTrailFromDate() {
        return auditTrailFromDate;
    }

    public void setAuditTrailFromDate(Date auditTrailFromDate) {
        this.auditTrailFromDate = auditTrailFromDate;
    }

    public Date getAuditTrailToDate() {
        return auditTrailToDate;
    }

    public void setAuditTrailToDate(Date auditTrailToDate) {
        this.auditTrailToDate = auditTrailToDate;
    }
    
    public void validate() {
        if (getAuditTrailFromDate() == null) {
            throw new ServiceBeanException("Audit Trail from date not provided for search");
        }
        if (getAuditTrailToDate() == null) {
            throw new ServiceBeanException("Audit Trail to date not provided for search");
        }
        if (getUser() == null) {
            throw new ServiceBeanException("Audit Trail User not specified for search");
        }
    }
    
}
