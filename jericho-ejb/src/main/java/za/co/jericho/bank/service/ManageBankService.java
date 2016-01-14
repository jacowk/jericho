package za.co.jericho.bank.service;

import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.bank.domain.Bank;
import za.co.jericho.bank.search.BankSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-06
 */
@Remote
public interface ManageBankService {
    
    public Bank addBank(Bank bank);

    public Bank updateBank(Bank bank);
    
    public Bank markBankDeleted(Bank bank);
    
    public Collection<Bank> searchBanks(BankSearchCriteria bankSearchCriteria);
    
    public Bank findBank(Object id);
    
    public Collection<Bank> findAllBanks();
    
}
