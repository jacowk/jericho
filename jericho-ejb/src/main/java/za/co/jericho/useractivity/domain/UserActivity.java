package za.co.jericho.useractivity.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-28
 */
@Entity
@Table(name="user_activity", schema="jericho")
public class UserActivity implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "entity_id")
    private Long entityId;
    @Column(name = "description")
    private String description; /* Add a description if no permission is applicable, 
                                    and for logging search criteria */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id")
    private Permission permission; /* Activities logged for a user is driven by permissions */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_user")
    private User activityUser;
    @Column(name = "activity_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activityDate;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public User getActivityUser() {
        return activityUser;
    }

    public void setActivityUser(User activityUser) {
        this.activityUser = activityUser;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }
    
}
