package za.co.jericho.audit;

import java.util.Date;
import za.co.jericho.audit.domain.AuditActivity;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 */
public class AuditActivityFactory {
    
    private static AuditActivityFactory auditActivityFactory;
    
    public static AuditActivityFactory getInstance() {
        if (auditActivityFactory == null) {
            auditActivityFactory = new AuditActivityFactory();
        }
        return auditActivityFactory;
    }
    
    public AuditActivity createAuditActivity(
            EntityName entityName,
            String serviceName,
            AuditActivityType auditActivityType,
            String description, 
            User currentUser) {
        AuditActivity auditActivity = new AuditActivity();
        auditActivity.setEntityId(currentUser.getId());
        auditActivity.setEntityName(entityName.getValue());
        auditActivity.setServiceName(serviceName);
        auditActivity.setDescription(description);
        auditActivity.setActivityType(auditActivityType.getValue());
        auditActivity.setCreateDate(new Date());
        auditActivity.setCreatedBy(currentUser);
        return auditActivity;
    }
    
}
