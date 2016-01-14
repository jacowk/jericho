package za.co.jericho.audit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.domain.*;
import za.co.jericho.exception.EntityValidationException;

@Entity
@Table(name="audit_activity")
public class AuditActivity extends AbstractEntity {

    @Column(name = "entity_id")
    private Long entityId; /* The primary key of entityName */
    @Column(name = "entity_name")
    private String entityName; /* The value in enum EntityName */
    @Column(name = "service_name")
    private String serviceName; /* The name of the method in the service bean called */
    @Column(name = "activity_type")
    private String activityType; /* The value in enum AuditActivityType */
    @Column(name = "description")
    private String description;

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public void validate() {
        throw new EntityValidationException("Not implemented");
    }

}