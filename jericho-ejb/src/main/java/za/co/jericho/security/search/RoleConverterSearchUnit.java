package za.co.jericho.security.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.security.domain.Role;

/**
 * This search unit is used to find the role object for the RoleConverter
 * 
 * @author Jaco Koekemoer
 * Date: 2016-01-08
 */
public class RoleConverterSearchUnit extends AbstractPersistenceUnit {

    private final String roleName;
    private Role role;
    
    public RoleConverterSearchUnit(EntityManager entityManager, String roleName) {
        super(entityManager);
        this.roleName = roleName;
    }
    
    @Override
    public void run() {
        List<Role> roles = new ArrayList<>();
        /* Include starts with, ends with and contains */
        if (roleName != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT r FROM Role r ");
            stringBuilder.append("WHERE r.name like :name ");
            roles = getEntityManager().createQuery(stringBuilder.toString())
                .setParameter("name", roleName)
                .getResultList();
            if (roles.size() > 0) {
                role = roles.get(0); /* Always return only 1 entry */
            }
            else {
                role = new Role();
                role.setName("Unknown Role");
            }
        }
    }

    public Role getRole() {
        return role;
    }
    
}
