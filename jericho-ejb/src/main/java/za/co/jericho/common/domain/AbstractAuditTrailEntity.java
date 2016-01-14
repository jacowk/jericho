package za.co.jericho.common.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import za.co.jericho.security.domain.User;
import za.co.jericho.useractivity.domain.UserActivity;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-28
 */
@MappedSuperclass
public abstract class AbstractAuditTrailEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "entity_id")
    private Long entityId;
    @Column(name = "audit_service_name")
    private String auditServiceName;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "audit_trail_user")
    private User auditTrailUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "audit_trail_date")
    private Date auditTrailDate;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_activity_id")
    private UserActivity userActivity;
    @Column(name = "deleted")
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getAuditServiceName() {
        return auditServiceName;
    }

    public void setAuditServiceName(String auditServiceName) {
        this.auditServiceName = auditServiceName;
    }

    public User getAuditTrailUser() {
        return auditTrailUser;
    }

    public void setAuditTrailUser(User auditTrailUser) {
        this.auditTrailUser = auditTrailUser;
    }

    public Date getAuditTrailDate() {
        return auditTrailDate;
    }

    public void setAuditTrailDate(Date auditTrailDate) {
        this.auditTrailDate = auditTrailDate;
    }

    public UserActivity getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(UserActivity userActivity) {
        this.userActivity = userActivity;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public abstract void validate();
    
}
