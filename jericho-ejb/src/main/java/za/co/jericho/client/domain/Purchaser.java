package za.co.jericho.client.domain;

import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.propertyflip.domain.PropertyFlip;

/**
 * A client can be a purchaser or a seller
 * @author Jaco Koekemoer
 * Date: 2015-11-07
 */
@Entity
@Table(name="purchaser", schema = "jericho")
public class Purchaser extends AbstractEntity {

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    @OneToOne(mappedBy="purchaser")
    @JoinColumn(name="property_flip_id")
    private PropertyFlip propertyFlip;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="purchaser_id")
    private Collection<Contact> contacts;

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    public Collection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("|ID: ");
            stringBuilder.append(getId());
        }
        return stringBuilder.toString();
    }

}