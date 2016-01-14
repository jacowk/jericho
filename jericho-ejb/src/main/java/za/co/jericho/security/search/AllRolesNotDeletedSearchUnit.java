package za.co.jericho.security.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.security.domain.Role;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-01-07
 */
public class AllRolesNotDeletedSearchUnit extends AbstractPersistenceUnit {
    
    private List<Role> roles = new ArrayList<>();

    public AllRolesNotDeletedSearchUnit(EntityManager entityManager) {
        super(entityManager);
    }
    
    @Override
    public void run() {
        LogManager.getRootLogger().info("AllRolesSearchUnit: run");
        StringBuilder searchStringBuilder = new StringBuilder();
        searchStringBuilder.append("SELECT r FROM Role r ");
        searchStringBuilder.append("WHERE r.deleted = false");
        roles = getEntityManager().createQuery(searchStringBuilder.toString())
            .getResultList();
    }
    
    public List<Role> getRoles() {
        return roles;
    }
    
}
