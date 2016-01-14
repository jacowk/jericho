package za.co.jericho.account.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.account.domain.Account;
import za.co.jericho.account.search.AccountSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
@Remote
public interface ManageAccountService {
    
    public Account addAccount(Account account);

    public Account updateAccount(Account account);

    public Account markAccountDeleted(Account account);

    public Collection<Account> searchAcounts(AccountSearchCriteria accountSearchCriteria);
    
    public BigDecimal calculateProfitOrLoss();
    
    public Account findAccount(Object id);
    
    public Collection<Account> findAllAccounts();
    
}
