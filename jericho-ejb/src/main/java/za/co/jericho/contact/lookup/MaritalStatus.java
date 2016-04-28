package za.co.jericho.contact.lookup;

import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.lookup.AbstractLookupEntity;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-04-16
 */
@Entity
@Table(name="lookup_marital_status", schema = "jericho")
public class MaritalStatus extends AbstractLookupEntity {
    
}
