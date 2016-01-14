package za.co.jericho.account.domain;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import za.co.jericho.account.lookup.AccountType;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.transaction.domain.Transaction;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
@Entity
@Table(name="account")
public class Account extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="property_flip_id")
    private PropertyFlip propertyFlip;
    @Column(name = "account_type")
    private AccountType accountType;//TODO
    @OneToMany(mappedBy = "account", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="transaction_id")
    private Collection<Transaction> transactions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransaction(Collection<Transaction> transactions) {
        this.transactions = transactions;
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