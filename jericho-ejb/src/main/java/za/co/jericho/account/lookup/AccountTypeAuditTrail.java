package za.co.jericho.account.lookup;

import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.lookup.AbstractLookupAuditTrailEntity;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-02-04
 */
@Entity
@Table(name="account_type_audit_trail", schema = "jericho")
public class AccountTypeAuditTrail extends AbstractLookupAuditTrailEntity {

    
}
