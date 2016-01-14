package za.co.jericho.security.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.Role;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 * This search unit is used to find the permission object for the PermissionConverter
 * 
 * @author Jaco Koekemoer
 * Date: 2016-01-08
 */
public class PermissionConverterSearchUnit extends AbstractPersistenceUnit {
    
    private final String permissionName;
    private Permission permission;
    
    public PermissionConverterSearchUnit(EntityManager entityManager, String 
        permissionName) {
        super(entityManager);
        this.permissionName = permissionName;
    }
    
    @Override
    public void run() {
        List<Permission> permissions = new ArrayList<>();
        /* Include starts with, ends with and contains */
        if (permissionName != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT p FROM Permission p ");
            stringBuilder.append("WHERE p.name like :name ");
            permissions = getEntityManager().createQuery(stringBuilder.toString())
                .setParameter("name", permissionName)
                .getResultList();
            if (permissions.size() > 0) {
                permission = permissions.get(0); /* Always return only 1 entry */
            }
            else {
                permission = new Permission();
                permission.setName("Unknown Permission");
            }
        }
    }

    public Permission getPermission() {
        return permission;
    }
    
}
