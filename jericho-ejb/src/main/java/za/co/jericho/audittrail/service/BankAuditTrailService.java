package za.co.jericho.audittrail.service;

import za.co.jericho.bank.domain.BankAuditTrail;
import za.co.jericho.bank.domain.Bank;/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-04
 */
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-12-04
 */
public class BankAuditTrailService extends AbstractAuditTrailService {
 
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Bank bank = null;
        if (entity instanceof Bank) {
            bank = (Bank) entity;
        }
        else {
            return null;
        }
        BankAuditTrail bankAuditTrail = new BankAuditTrail();
        bankAuditTrail.setEntityId(bank.getId());
        bankAuditTrail.setName(bank.getName());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        bankAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_BANK) {
            bankAuditTrail.setAuditTrailDate(bank.getCreateDate());
            bankAuditTrail.setAuditTrailUser(bank.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_BANK) {
            bankAuditTrail.setAuditTrailDate(bank.getLastModifyDate());
            bankAuditTrail.setAuditTrailUser(bank.getLastModifiedBy());
        }
        bankAuditTrail.setDeleted(bank.isDeleted());
        return bankAuditTrail;
    }
    
}
