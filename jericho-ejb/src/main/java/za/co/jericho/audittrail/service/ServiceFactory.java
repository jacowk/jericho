package za.co.jericho.audittrail.service;

import za.co.jericho.security.ServiceName;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-04
 */
public interface ServiceFactory {
    
    public AuditTrailService createAuditTrailService(ServiceName serviceName);
    
}
