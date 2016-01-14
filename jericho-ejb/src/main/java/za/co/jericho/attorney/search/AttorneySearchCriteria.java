package za.co.jericho.attorney.search;

import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.contact.search.ContactSearchCriteria;

public class AttorneySearchCriteria extends AbstractSearchCriteria {

    private String name;
    private ContactSearchCriteria contactSearchCriteria;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactSearchCriteria getContactSearchCriteria() {
        return contactSearchCriteria;
    }

    public void setContactSearchCriteria(ContactSearchCriteria contactSearchCriteria) {
        this.contactSearchCriteria = contactSearchCriteria;
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
        if (getContactSearchCriteria() != null) {
            stringBuilder.append("\nContactSearchCriteria: \n");
            stringBuilder.append(getContactSearchCriteria().toString());
        }
        return stringBuilder.toString();
    }
    
}