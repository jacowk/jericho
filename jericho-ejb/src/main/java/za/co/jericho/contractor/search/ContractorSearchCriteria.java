package za.co.jericho.contractor.search;

import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.contact.search.ContactSearchCriteria;
import za.co.jericho.contractor.lookup.ContractorType;
import za.co.jericho.propertyflip.search.PropertyFlipSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-14
 */
public class ContractorSearchCriteria extends AbstractSearchCriteria {

    private String name;
    private String workDescription;
    private ContractorType contractorType;
    private PropertyFlipSearchCriteria propertyFlipSearchCriteria;
    private ContactSearchCriteria contactSearchCriteria;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public ContractorType getContractorType() {
        return contractorType;
    }

    public void setContractorType(ContractorType contractorType) {
        this.contractorType = contractorType;
    }

    public PropertyFlipSearchCriteria getPropertyFlipSearchCriteria() {
        return propertyFlipSearchCriteria;
    }

    public void setPropertyFlipSearchCriteria(PropertyFlipSearchCriteria 
        propertyFlipSearchCriteria) {
        this.propertyFlipSearchCriteria = propertyFlipSearchCriteria;
    }

    public ContactSearchCriteria getContactSearchCriteria() {
        return contactSearchCriteria;
    }

    public void setContactSearchCriteria(ContactSearchCriteria contactSearchCriteria) {
        this.contactSearchCriteria = contactSearchCriteria;
    }
    
}