package za.co.jericho.account.service;

import za.co.jericho.account.lookup.AccountType;
import za.co.jericho.account.lookup.AccountTypeAuditTrail;
import za.co.jericho.audittrail.service.AbstractAuditTrailService;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-04
 */
public class AccountTypeAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        AccountType accountType = null;
        if (entity instanceof AccountType) {
            accountType = (AccountType) entity;
        }
        else {
            return null;
        }
        AccountTypeAuditTrail accountTypeAuditTrail = new AccountTypeAuditTrail();
        accountTypeAuditTrail.setEntityId(accountType.getId());
        accountTypeAuditTrail.setDescription(accountType.getDescription());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        accountTypeAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_ACCOUNT_TYPE) {
            accountTypeAuditTrail.setAuditTrailDate(accountType.getCreateDate());
            accountTypeAuditTrail.setAuditTrailUser(accountType.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_ACCOUNT_TYPE) {
            accountTypeAuditTrail.setAuditTrailDate(accountType.getLastModifyDate());
            accountTypeAuditTrail.setAuditTrailUser(accountType.getLastModifiedBy());
        }
        accountTypeAuditTrail.setDeleted(accountType.isDeleted());
        return accountTypeAuditTrail;
    }
    
}
