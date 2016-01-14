package za.co.jericho.audittrail.service;

import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.estateagent.domain.EstateAgent;
import za.co.jericho.estateagent.domain.EstateAgentAuditTrail;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-30
 */
public class EstateAgentAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        EstateAgent estateAgent = null;
        if (entity instanceof EstateAgent) {
            estateAgent = (EstateAgent) entity;
        }
        else {
            return null;
        }
        EstateAgentAuditTrail estateAgentAuditTrail = new EstateAgentAuditTrail();
        estateAgentAuditTrail.setEntityId(estateAgent.getId());
        estateAgentAuditTrail.setName(estateAgent.getName());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        estateAgentAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_ESTATE_AGENT) {
            estateAgentAuditTrail.setAuditTrailDate(estateAgent.getCreateDate());
            estateAgentAuditTrail.setAuditTrailUser(estateAgent.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_ESTATE_AGENT) {
            estateAgentAuditTrail.setAuditTrailDate(estateAgent.getLastModifyDate());
            estateAgentAuditTrail.setAuditTrailUser(estateAgent.getLastModifiedBy());
        }
        estateAgentAuditTrail.setDeleted(estateAgent.isDeleted());
        return estateAgentAuditTrail;
    }
    
}
