package za.co.jericho.client.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.LogManager;
import za.co.jericho.client.domain.Seller;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-05-14
 */
public class SellerSearchUnit extends AbstractPersistenceUnit {
    
    private final SellerSearchCriteria sellerSearchCriteria;
    private List<Seller> sellers = new ArrayList<>();
    
    public SellerSearchUnit(EntityManager entityManager, SellerSearchCriteria 
        sellerSearchCriteria) {
        super(entityManager);
        this.sellerSearchCriteria = sellerSearchCriteria;
    }
    
    @Override
    public void run() {
        LogManager.getRootLogger().info("SellerSearchUnit: run");
        sellers.clear();
        if (sellerSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchSellersStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            /* Prepare parameters values */
            String firstname = stringConvertor.convertForDatabaseSearch
                (sellerSearchCriteria.getFirstname(), sellerSearchCriteria.getSearchType());
            String surname = stringConvertor.convertForDatabaseSearch
                (sellerSearchCriteria.getSurname(), sellerSearchCriteria.getSearchType());
            Long referenceNumber = sellerSearchCriteria.getReferenceNo();
            
            /* Prepare EJBQL statement */
            searchSellersStringBuilder.append("SELECT s FROM Seller s ");
            searchSellersStringBuilder.append("WHERE s.contact.firstname like :firstname ");
            searchSellersStringBuilder.append("AND s.contact.surname like :surname ");
            if (referenceNumber != null) {
                searchSellersStringBuilder.append("AND s.propertyFlip.referenceNumber = :referenceNumber ");
            }
            
            /* Prepare query */
            Query query = getEntityManager().createQuery(searchSellersStringBuilder.toString());
            query.setParameter("firstname", firstname);
            query.setParameter("surname", surname);
            if (referenceNumber != null) {
                query.setParameter("referenceNumber", referenceNumber);
            }
            
            /* Execute query */
            sellers = query.getResultList();
        }
        else {
            throw new ServiceBeanException("Seller search criteria not provided");
        }
    }

    public List<Seller> getSellers() {
        return sellers;
    }
    
}
