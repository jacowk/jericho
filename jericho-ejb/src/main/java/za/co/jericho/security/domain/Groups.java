package za.co.jericho.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;

/**
 *
 * @author user
 */
@Entity
@Table(name="groups", schema="jericho")
public class Groups extends AbstractEntity {
    
    private static final long serialVersionUID = 1L;
    @Column(name = "groupname", nullable = false)
    private String groupname;
    @Column(name = "username", nullable = false)
    private String username;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public void validate() {
        throw new EntityValidationException("Not implemented");
    }
    
}
