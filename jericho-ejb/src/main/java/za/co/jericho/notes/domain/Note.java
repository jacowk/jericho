package za.co.jericho.notes.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Entity
@Table(name="note", schema="jericho")
public class Note extends AbstractEntity {

    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="property_flip_id")
    private PropertyFlip propertyFlip;
    
    /**
     * Get a description
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set a description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get a property flip
     * @return PropertyFlip
     */
    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }
    
    /**
     * Set a property flip
     * @param propertyFlip 
     */
    public void setPropertyFlip(PropertyFlip propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (stringValidator.isNullOrEmpty(getDescription())) {
            throw new EntityValidationException("A description must be provided");
        }
    }

}