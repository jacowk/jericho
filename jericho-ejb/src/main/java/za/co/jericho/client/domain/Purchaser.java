package za.co.jericho.client.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.propertyflip.domain.PropertyFlip;

/**
 * A client can be a purchaser or a seller
 * @author Jaco Koekemoer
 * Date: 2015-11-07
 */
@Entity
@Table(name="purchaser", schema = "jericho")
public class Purchaser extends AbstractEntity {

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "property_flip_id")
    private PropertyFlip propertyFlip;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="contact_id")
    private Contact contact;

    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContacts(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void validate() {
        if (getPropertyFlip() == null) {
            throw new EntityValidationException("A property flip must be provided for the seller");
        }
        if (getContact() == null) {
            throw new EntityValidationException("A contact must be provided for the seller");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getPropertyFlip() != null) {
            stringBuilder.append("Property Flip ID: ");
            stringBuilder.append(getPropertyFlip().getId());
        }
        if (getContact() != null) {
            stringBuilder.append("Contact ID: ");
            stringBuilder.append(getContact().getId());
        }
        return stringBuilder.toString();
    }

}