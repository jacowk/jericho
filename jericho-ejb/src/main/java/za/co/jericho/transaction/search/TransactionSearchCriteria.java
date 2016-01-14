package za.co.jericho.transaction.search;

import za.co.jericho.account.search.AccountSearchCriteria;
import za.co.jericho.common.search.AbstractSearchCriteria;

public class TransactionSearchCriteria extends AbstractSearchCriteria {

    private AccountSearchCriteria accountSearchCriteria;
    private Long transactionId;

    public AccountSearchCriteria getAccountSearchCriteria() {
        return accountSearchCriteria;
    }

    public void setAccountSearchCriteria(AccountSearchCriteria accountSearchCriteria) {
        this.accountSearchCriteria = accountSearchCriteria;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    
}