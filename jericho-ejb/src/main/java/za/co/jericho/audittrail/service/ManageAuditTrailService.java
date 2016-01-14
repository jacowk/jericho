package za.co.jericho.audittrail.service;

import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.audittrail.search.AuditTrailSearchCriteria;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-28
 */
@Remote
public interface ManageAuditTrailService {
    
    public AbstractAuditTrailEntity addAuditTrail(AbstractAuditTrailEntity auditTrail);
    
    public Collection<AbstractAuditTrailEntity> searchAuditTrails(AuditTrailSearchCriteria 
        auditTrailSearchCriteria);
    
    public void createAuditTrail(ServiceName serviceName, Object entity);
    
}
