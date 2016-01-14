package za.co.jericho.audittrail.service;

import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-04
 */
public interface AuditTrailService {
    
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity);
    
}
