package za.co.jericho.security.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.security.domain.Role;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-07
 */
public class RoleSearchUnit extends AbstractPersistenceUnit {

    private final RoleSearchCriteria roleSearchCriteria;
    private List<Role> roles = new ArrayList<>();
    
    public RoleSearchUnit(EntityManager entityManager, RoleSearchCriteria 
        roleSearchCriteria) {
        super(entityManager);
        this.roleSearchCriteria = roleSearchCriteria;
    }
    
    @Override
    public void run() {
        LogManager.getRootLogger().info("RolesSearchUnit: run");
        /* Include starts with, ends with and contains */
        if (roleSearchCriteria != null) {
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT r FROM Role r ");
            searchUsersStringBuilder.append("WHERE r.name like :name ");
            searchUsersStringBuilder.append("AND r.deleted = false");
            roles = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("name", stringConvertor
                    .convertForDatabaseSearchContains(roleSearchCriteria.getName()))
                .getResultList();
        }
    }

    public List<Role> getRoles() {
        return roles;
    }
    
}
