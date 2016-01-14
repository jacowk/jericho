package za.co.jericho.transaction.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.transaction.domain.Transaction;
import za.co.jericho.transaction.domain.TransactionItem;
import za.co.jericho.transaction.search.TransactionSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
@Remote
public interface ManageTransactionService {
    
    public Transaction addTransaction(Transaction transaction);
    
    public Transaction updateTransaction(Transaction transaction);
    
    public Transaction markTransactionDeleted(Transaction transaction);

    public List<Transaction> searchTransactions(TransactionSearchCriteria 
        transactionSearchCriteria);
    
    public TransactionItem addTransactionItem(TransactionItem transactionItem);
        
    public TransactionItem updateTransactionItem(TransactionItem transactionItem);
        
    public TransactionItem markTransactionItemDeleted(TransactionItem transactionItem);
    
    public List<TransactionItem> searchTransactionItems(TransactionSearchCriteria 
        transactionSearchCriteria);
    
}
