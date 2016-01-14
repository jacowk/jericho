package za.co.jericho.estateagent.domain;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.estateagent.lookup.EstateAgentType;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Entity
@Table(name="estate_agent", schema = "jericho")
public class EstateAgent extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "estateAgents")
    private Collection<PropertyFlip> propertyFlip;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "estate_agent_id")
    private Collection<Contact> contact;
    @Column(name = "estate_agent_type")
    private EstateAgentType estateAgentType; //TODO

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<PropertyFlip> getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(Collection<PropertyFlip> propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    public Collection<Contact> getContact() {
        return contact;
    }

    public void setContact(Collection<Contact> contact) {
        this.contact = contact;
    }

    public EstateAgentType getEstateAgentType() {
        return estateAgentType;
    }

    public void setEstateAgentType(EstateAgentType estateAgentType) {
        this.estateAgentType = estateAgentType;
    }

    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getName() == null || stringValidator.isNullOrEmpty(getName())) {
            throw new EntityValidationException("Estate Agent name must be provided");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getName() != null) {
            stringBuilder.append("\nName: ");
            stringBuilder.append(getName());
        }
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}