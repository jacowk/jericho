package za.co.jericho.contractor.domain;

import java.util.ArrayList;
import java.util.List;
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
import za.co.jericho.contractor.lookup.ContractorType;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;


/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-13
 */
@Entity
@Table(name="contractor", schema = "jericho")
public class Contractor extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "contractor_type")
    private ContractorType contractorType; //TODO Sort out the type
    @Column(name = "work_description")
    private String workDescription;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "contractors")
    private List<PropertyFlip> propertyFlips = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "contractor_id")
    private List<Contact> contacts = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContractorType getContractorType() {
        return contractorType;
    }

    public void setContractorType(ContractorType contractorType) {
        this.contractorType = contractorType;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public List<PropertyFlip> getPropertyFlips() {
        return propertyFlips;
    }

    public void setPropertyFlips(List<PropertyFlip> propertyFlips) {
        this.propertyFlips = propertyFlips;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    
    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getName() == null || stringValidator.isNullOrEmpty(getName())) {
            throw new EntityValidationException("Contractor name must be provided");
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
        if (getWorkDescription() != null) {
            stringBuilder.append("\nWork Descrption: ");
            stringBuilder.append(getWorkDescription());
        }
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}