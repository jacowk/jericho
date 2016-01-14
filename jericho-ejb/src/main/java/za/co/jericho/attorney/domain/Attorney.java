package za.co.jericho.attorney.domain;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import za.co.jericho.attorney.lookup.AttorneyType;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-06
 */
@Entity
@Table(name="attorney", schema = "jericho")
public class Attorney extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attorneys")
    private Collection<PropertyFlip> propertyFlip;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="attorney_id")
    private Collection<Contact> contact;
    @Column(name = "attorney_type")
    private AttorneyType attorneyType; //TODO

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

    public AttorneyType getAttorneyType() {
        return attorneyType;
    }

    public void setAttorneyType(AttorneyType attorneyType) {
        this.attorneyType = attorneyType;
    }

    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getName() == null || stringValidator.isNullOrEmpty(getName())) {
            throw new EntityValidationException("Attorney name must be provided");
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