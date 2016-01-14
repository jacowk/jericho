package za.co.jericho.audittrail.service;

import za.co.jericho.contractor.domain.Contractor;
import za.co.jericho.contractor.domain.ContractorAuditTrail;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-29
 */
public class ContractorAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Contractor contractor = null;
        if (entity instanceof Contractor) {
            contractor = (Contractor) entity;
        }
        else {
            return null;
        }
        ContractorAuditTrail contractorAuditTrail = new ContractorAuditTrail();
        contractorAuditTrail.setEntityId(contractor.getId());
        contractorAuditTrail.setName(contractor.getName());
        contractorAuditTrail.setWorkDescription(contractor.getWorkDescription());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        contractorAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_CONTRACTOR) {
            contractorAuditTrail.setAuditTrailDate(contractor.getCreateDate());
            contractorAuditTrail.setAuditTrailUser(contractor.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_CONTRACTOR) {
            contractorAuditTrail.setAuditTrailDate(contractor.getLastModifyDate());
            contractorAuditTrail.setAuditTrailUser(contractor.getLastModifiedBy());
        }
        contractorAuditTrail.setDeleted(contractor.isDeleted());
        return contractorAuditTrail;
    }
    
}
