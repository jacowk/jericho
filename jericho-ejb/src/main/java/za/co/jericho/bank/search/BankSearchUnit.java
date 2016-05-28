package za.co.jericho.bank.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import za.co.jericho.bank.domain.Bank;
import za.co.jericho.common.domain.unit.AbstractPersistenceUnit;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-05-28
 */
public class BankSearchUnit extends AbstractPersistenceUnit {
    
    private final BankSearchCriteria bankSearchCriteria;
    private List<Bank> banks = new ArrayList<>();
    
    public BankSearchUnit(EntityManager entityManager, 
        BankSearchCriteria bankSearchCriteria) {
        super(entityManager);
        this.bankSearchCriteria = bankSearchCriteria;
    }
    
    @Override
    public void run() {
        LogManager.getRootLogger().info("BankSearchUnit: run");
        banks.clear();
        if (bankSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchBanksStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchBanksStringBuilder.append("SELECT b FROM Bank b ");
            searchBanksStringBuilder.append("WHERE b.name like :name ");
            String name = stringConvertor.convertForDatabaseSearch
                (bankSearchCriteria.getName(), bankSearchCriteria.getSearchType());
            
            banks = getEntityManager().createQuery(searchBanksStringBuilder.toString())
                .setParameter("name", name)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Bank search criteria not provided");
        }
    }
    
    public List<Bank> getBanks() {
        return banks;
    }
    
}
