package za.co.jericho.account.service;

import java.math.BigDecimal;
import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.account.domain.Account;
import za.co.jericho.account.lookup.AccountType;
import za.co.jericho.account.search.AccountSearchCriteria;
import za.co.jericho.account.search.AccountTypeSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
@Remote
public interface ManageAccountService {
    
    /* Account Services */
    public Account addAccount(Account account);

    public Account updateAccount(Account account);

    public Account markAccountDeleted(Account account);

    public Collection<Account> searchAccounts(AccountSearchCriteria accountSearchCriteria);
    
    public BigDecimal calculateProfitOrLoss();
    
    public Account findAccount(Object id);
    
    public Collection<Account> findAllAccounts();
    
    /* Account Type Services */
    public AccountType addAccountType(AccountType accountType);

    public AccountType updateAccountType(AccountType accountType);

    public AccountType markAccountTypeDeleted(AccountType accountType);

    public Collection<AccountType> searchAccountTypes(AccountTypeSearchCriteria 
        accountTypeSearchCriteria);
    
    public AccountType findAccountType(Object id);
    
    public Collection<AccountType> findAllAccountTypes();
    
}
