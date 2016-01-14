/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.audit;

import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-13
 */
public interface AuditActivityLogger {
    
    public void logAuditData(EntityName entityName, String serviceName, 
        AuditActivityType activityType, String description, User currentUser);
    
}
