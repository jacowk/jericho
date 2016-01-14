package za.co.jericho.security.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.search.PermissionSearchCriteria;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-07
 */
public class PermissionSearchUnit extends AbstractPersistenceUnit {
    
    private PermissionSearchCriteria permissionSearchCriteria;
    private List<Permission> permissions = new ArrayList<>();
    
    public PermissionSearchUnit(EntityManager entityManager, 
        PermissionSearchCriteria permissionSearchCriteria) {
        super(entityManager);
        this.permissionSearchCriteria = permissionSearchCriteria;
    }
    
    @Override
    public void run() {
        LogManager.getRootLogger().info("PermissionsSearchUnit: run");
        /* Include starts with, ends with and contains */
        List<Permission> permissions = new ArrayList<>();
        if (permissionSearchCriteria != null) {
            StringConvertor stringConvertor = new StringDataConvertor();
            /* Prepare search criteria */
            String name = stringConvertor.convertForDatabaseSearch
                (permissionSearchCriteria.getName(), permissionSearchCriteria.getSearchType());
            String serviceName = stringConvertor.convertForDatabaseSearchWithNullIfEmpty
                (permissionSearchCriteria.getServiceName(), permissionSearchCriteria.getSearchType());
            /* Prepare JPQL statement */
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT p FROM Permission p ");
            searchUsersStringBuilder.append("WHERE p.name like :name ");
            if (permissionSearchCriteria.getServiceName() != null) {
                searchUsersStringBuilder.append("AND p.serviceName like :serviceName ");
            }
            searchUsersStringBuilder.append("AND p.deleted = false");
            
            LogManager.getRootLogger().info("Name: " + name);
            LogManager.getRootLogger().info("Service Name: " + serviceName);
            
            /* Execute Statement */
            Query query = getEntityManager().createQuery(searchUsersStringBuilder.toString());
            query.setParameter("name", name);
            if (permissionSearchCriteria.getServiceName() != null) {
                query.setParameter("serviceName", serviceName);
            }
            permissions = query.getResultList();
        }
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
    
}
