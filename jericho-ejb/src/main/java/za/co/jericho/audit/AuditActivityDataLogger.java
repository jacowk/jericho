/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.audit;

import javax.ejb.EJB;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.audit.service.ManageAuditActivityService;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-13
 */
public class AuditActivityDataLogger implements AuditActivityLogger {

    @EJB
    private ManageAuditActivityService manageAuditActivityService;
    
    @Override
    public void logAuditData(EntityName entityName, String serviceName, 
        AuditActivityType activityType, String description, User currentUser) {
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (entityName, serviceName, activityType, 
            description, currentUser));
    }
    
}
