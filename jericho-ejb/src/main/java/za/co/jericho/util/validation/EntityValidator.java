/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.util.validation;

import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.useractivity.domain.UserActivity;

/**
 *
 * @author Jaco Koekemoer
 */
public interface EntityValidator {
    
    public boolean isValidateEntityBeforeCreate(AbstractEntity entity);
    
    public boolean isValidateEntityBeforeUpdate(AbstractEntity entity);
    
    public boolean isValidateUserActivityEntityBeforeCreate(UserActivity 
        userActivity);
    
    public boolean isValidateAuditTrailEntityBeforeCreate(AbstractAuditTrailEntity 
        abstractAuditTrailEntity);
    
}
