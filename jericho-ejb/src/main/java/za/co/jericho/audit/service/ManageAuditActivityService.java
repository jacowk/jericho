package za.co.jericho.audit.service;

import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.audit.domain.AuditActivity;
import za.co.jericho.audit.search.AuditActivitySearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-13
 */
@Remote
public interface ManageAuditActivityService  {
    
    public AuditActivity addAuditActivity(AuditActivity auditActivity);
    
    public Collection<AuditActivity> searchAuditActivities(AuditActivitySearchCriteria auditActivitySearchCriteria);
    
}
