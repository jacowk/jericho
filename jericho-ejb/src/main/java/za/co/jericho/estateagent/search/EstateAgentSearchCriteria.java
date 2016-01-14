package za.co.jericho.estateagent.search;

import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.contact.search.ContactSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-10
 */
public class EstateAgentSearchCriteria extends AbstractSearchCriteria {

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
    
}