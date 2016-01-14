package za.co.jericho.attorney.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import za.co.jericho.attorney.domain.Attorney;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.search.RoleSearchCriteria;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-07
 */

public class AttorneySearchUnit extends AbstractPersistenceUnit {
    
    private final AttorneySearchCriteria attorneySearchCriteria;
    private List<Attorney> attorneys = new ArrayList<>();
    
    public AttorneySearchUnit(EntityManager entityManager, AttorneySearchCriteria 
        attorneySearchCriteria) {
        super(entityManager);
        this.attorneySearchCriteria = attorneySearchCriteria;
    }
    
    @Override
    public void run() {
        LogManager.getRootLogger().info("AttorneySearchUnit: run");
        List<Attorney> attorneys = new ArrayList<>();
        if (attorneySearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchAttorneysStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchAttorneysStringBuilder.append("SELECT a FROM Attorney a ");
            searchAttorneysStringBuilder.append("WHERE a.name like :name ");
            searchAttorneysStringBuilder.append("WHERE a.deleted = false ");
            String name = stringConvertor.convertForDatabaseSearch
                (attorneySearchCriteria.getName(), attorneySearchCriteria.getSearchType());
            attorneys = getEntityManager().createQuery(searchAttorneysStringBuilder.toString())
                .setParameter("name", name)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Attorney search criteria not provided");
        }
    }

    public List<Attorney> getAttorneys() {
        return attorneys;
    }
    
}
