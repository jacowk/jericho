package za.co.jericho.propertyflip.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-05-25
 */
public class PropertyFlipSearchUnit extends AbstractPersistenceUnit {

    private final PropertyFlipSearchCriteria propertyFlipSearchCriteria;
    private List<PropertyFlip> propertyFlips = new ArrayList<>();
    
    public PropertyFlipSearchUnit(EntityManager entityManager, 
        PropertyFlipSearchCriteria propertyFlipSearchCriteria) {
        super(entityManager);
        this.propertyFlipSearchCriteria = propertyFlipSearchCriteria;
    }
    
    @Override
    public void run() {
        LogManager.getRootLogger().info("PropertyFlipSearchUnit: run");
        propertyFlips.clear();
        if (propertyFlipSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
//            User user = userSearchCriteria.getUser();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT pf FROM PropetyFlip pf ");
            searchUsersStringBuilder.append("WHERE pf.referenceNumber like :referenceNumber ");
            searchUsersStringBuilder.append("AND pf.titleDeedNumber like :titleDeedNumber ");
            searchUsersStringBuilder.append("AND pf.caseNumber like :caseNumber ");
            String referenceNumber = stringConvertor.convertForDatabaseSearch
                (propertyFlipSearchCriteria.getReferenceNumber(), 
                propertyFlipSearchCriteria.getSearchType());
            String titleDeedNumber = stringConvertor.convertForDatabaseSearch
                (propertyFlipSearchCriteria.getTitleDeedNumber(), 
                propertyFlipSearchCriteria.getSearchType());
            String caseNumber = stringConvertor.convertForDatabaseSearch
                (propertyFlipSearchCriteria.getCaseNumber(), 
                propertyFlipSearchCriteria.getSearchType());
            propertyFlips = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("referenceNumber", referenceNumber)
                .setParameter("titleDeedNumber", titleDeedNumber)
                .setParameter("caseNumber", caseNumber)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Property Flip search criteria not provided");
        }
    }
    
    public List<PropertyFlip> getPropertyFlips() {
        return propertyFlips;
    }
    
}
