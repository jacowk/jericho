package za.co.jericho.transaction.domain;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import za.co.jericho.account.domain.Account;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.transaction.lookup.TransactionType;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-16
 */
@Entity
@Table(name="transaction", schema="jericho")
public class Transaction extends AbstractEntity {

    @OneToMany(mappedBy = "transaction", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="transaction_item_id")
    private Collection<TransactionItem> transactionItems;
    @Column(name = "transaction_type")
    private TransactionType transactionType; //TODO
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    
    public Collection<TransactionItem> getTransactionItems() {
        return transactionItems;
    }

    public void setTransactionItems(Collection<TransactionItem> transactionItems) {
        this.transactionItems = transactionItems;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("|ID: ");
            stringBuilder.append(getId());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}