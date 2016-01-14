package za.co.jericho.security.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.security.domain.Permission;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-07
 */
public class AllPermissionsNotDeletedSearchUnit extends AbstractPersistenceUnit {

    private List<Permission> permissions = new ArrayList<>();

    public AllPermissionsNotDeletedSearchUnit(EntityManager entityManager) {
        super(entityManager);
    }
    
    @Override
    public void run() {
        LogManager.getRootLogger().info("AllRolesSearchUnit: run");
        StringBuilder searchStringBuilder = new StringBuilder();
        searchStringBuilder.append("SELECT p FROM Permission p ");
        searchStringBuilder.append("WHERE p.deleted = false");
        permissions = getEntityManager().createQuery(searchStringBuilder.toString())
            .getResultList();
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
    
}
