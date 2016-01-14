package za.co.jericho.util.validation;

import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.exception.EntityValidatorException;
import za.co.jericho.useractivity.domain.UserActivity;

/**
 *
 * @author Jaco Koekemoer
 */
public class EntityStateValidator implements EntityValidator {

    @Override
    public boolean isValidateEntityBeforeCreate(AbstractEntity entity) {
        if (entity == null) {
            throw new EntityValidationException("Entity cannot be created. Entity is null.");
        }
        if (entity.getCreateDate() == null)
        {
            throw new EntityValidationException("Entity cannot be created. Create date must be present.");
        }
        if (entity.getCreatedBy() == null)
        {
            throw new EntityValidationException("Entity cannot be created. Created by must be present.");
        }
        return true;
    }

    @Override
    public boolean isValidateEntityBeforeUpdate(AbstractEntity entity) {
        if (entity == null) {
            throw new EntityValidatorException("Entity cannot be updated. Entity is null.");
        }
//        if (entity.getId() == null) {
//            throw new EntityValidatorException("Entity cannot be updated. Id is not provided.");
//        }
        if (entity.getLastModifyDate() == null)
        {
            throw new EntityValidatorException("Entity cannot be created. Last modify date must be present.");
        }
        if (entity.getLastModifiedBy() == null)
        {
            throw new EntityValidatorException("Entity cannot be created. Last modified by must be present.");
        }
        return true;
    }
    
    @Override
    public boolean isValidateUserActivityEntityBeforeCreate(UserActivity userActivity) {
        if (userActivity == null) {
            throw new EntityValidatorException("UserActivity is null and cannot be created.");
        }
//        if (userActivity.getEntityId() == null) {
//            throw new EntityValidatorException("UserActivity cannot be created. An Entity Id must be specified.");
//        }
        if ((userActivity.getDescription() == null || 
            userActivity.getDescription().length() == 0) &&
            userActivity.getPermission() == null) {
            throw new EntityValidatorException("UserActivity cannot be created. "
                + "Either a description or a user permission must be specified.");
        }
        if (userActivity.getActivityDate() == null)
        {
            throw new EntityValidatorException("UserActivity cannot be created. Activity date must be specified.");
        }
        if (userActivity.getActivityUser() == null)
        {
            throw new EntityValidatorException("UserActivity cannot be created. User by must be specified.");
        }
        return true;
    }
    
    @Override
    public boolean isValidateAuditTrailEntityBeforeCreate(AbstractAuditTrailEntity abstractAuditTrailEntity) {
        if (abstractAuditTrailEntity == null) {
            throw new EntityValidatorException("AuditTrail is null and cannot be created.");
        }
        if (abstractAuditTrailEntity.getAuditTrailDate() == null)
        {
            throw new EntityValidatorException("AuditTrail cannot be created. Audit trail date must be specified.");
        }
        if (abstractAuditTrailEntity.getAuditTrailUser() == null)
        {
            throw new EntityValidatorException("AuditTrail cannot be created. Audit trail user by must be specified.");
        }
        return true;
    }
    
}
