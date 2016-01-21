package za.co.jericho.audittrail.service;

import za.co.jericho.exception.AuditTrailException;
import za.co.jericho.security.ServiceName;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-04
 */
public class AuditTrailServiceFactory implements ServiceFactory {
    
    private static ServiceFactory serviceFactory = null;
    
    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new AuditTrailServiceFactory();
        }
        return serviceFactory;
    }
    
    @Override
    public AuditTrailService createAuditTrailService(ServiceName serviceName) {
        if (serviceName == ServiceName.ADD_ATTORNEY || 
            serviceName == ServiceName.UPDATE_ATTORNEY) {
            return new AttorneyAuditTrailService();
        }
        else if (serviceName == ServiceName.ADD_BANK || 
            serviceName == ServiceName.UPDATE_BANK) {
            return new BankAuditTrailService();
        }
        else if (serviceName == ServiceName.ADD_CONTRACTOR || 
            serviceName == ServiceName.UPDATE_CONTRACTOR) {
            return new ContractorAuditTrailService();
        }
        else if (serviceName == ServiceName.ADD_ESTATE_AGENT || 
            serviceName == ServiceName.UPDATE_ESTATE_AGENT) {
            return new EstateAgentAuditTrailService();
        }
        else if (serviceName == ServiceName.ADD_ROLE || 
            serviceName == ServiceName.UPDATE_ROLE) {
            return new RoleAuditTrailService();
        }
        else if (serviceName == ServiceName.ADD_PERMISSION || 
            serviceName == ServiceName.UPDATE_PERMISSION) {
            return new PermissionAuditTrailService();
        }
        else if (serviceName == ServiceName.ADD_AREA || 
            serviceName == ServiceName.UPDATE_AREA) {
            return new AreaAuditTrailService();
        }
        else if (serviceName == ServiceName.ADD_GREATER_AREA || 
            serviceName == ServiceName.UPDATE_GREATER_AREA) {
            return new GreaterAreaAuditTrailService();
        }
        else if (serviceName == ServiceName.ADD_SUBURB || 
            serviceName == ServiceName.UPDATE_SUBURB) {
            return new SuburbAuditTrailService();
        }
        throw new AuditTrailException("Audit trail service not found");
    }
    
}
