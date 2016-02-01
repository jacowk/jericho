package za.co.jericho.account.lookup;

import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.lookup.AbstractLookupEntity;

/**
 * 
 * @author Jaco Koekemoer
 * Date: 2016-02-01
 */
@Entity
@Table(name="account_type", schema = "jericho")
public class AccountType extends AbstractLookupEntity {
    
}