package za.co.jericho.propertyflip.domain;

import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import za.co.jericho.account.domain.Account;
import za.co.jericho.attorney.domain.Attorney;
import za.co.jericho.bank.domain.Bank;
import za.co.jericho.client.domain.Purchaser;
import za.co.jericho.client.domain.Seller;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.contractor.domain.Contractor;
import za.co.jericho.diary.domain.Diary;
import za.co.jericho.documents.domain.Document;
import za.co.jericho.estateagent.domain.EstateAgent;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.notes.domain.Note;
import za.co.jericho.property.domain.Property;
import za.co.jericho.util.conversion.BigDecimalDataFormatter;
import za.co.jericho.util.conversion.BigDecimalFormatter;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 * 
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Entity
@Table(name="property_flip", schema = "jericho")
@NamedQueries({
    @NamedQuery(name = "findPropertyFlipByReferenceNumber", query = "SELECT pf FROM PropertyFlip pf WHERE pf.referenceNumber = :referenceNumber"),
    @NamedQuery(name = "findPropertyFlipByTitleDeedNumber", query = "SELECT pf FROM PropertyFlip pf WHERE pf.titleDeedNumber = :titleDeedNumber"),
    @NamedQuery(name = "findPropertyFlipByCaseNumber", query = "SELECT pf FROM PropertyFlip pf WHERE pf.caseNumber = :caseNumber")
})
public class PropertyFlip extends AbstractEntity { //Change to Strategy

    @Column(name = "reference_number")
    private Long referenceNumber;
    @Column(name = "title_deed_number")
    private String titleDeedNumber;
    @Column(name = "case_number")
    private String caseNumber;
    @Column(name = "selling_price")
    private BigDecimal sellingPrice;
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
    private Property property;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "property_flip_attorney", joinColumns = { 
        @JoinColumn(name = "property_flip_id", nullable = false, updatable = false) }, 
        inverseJoinColumns = { 
            @JoinColumn(name = "attorney_id", nullable = false, updatable = false) })
    private Collection<Attorney> attorneys;
    @OneToMany(mappedBy = "propertyFlip", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="note_id")
    private Collection<Note> notes;
    @OneToMany(mappedBy = "propertyFlip", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Collection<Account> accounts;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "diary_id")
    private Diary diary;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "property_flip_contractor", joinColumns = { 
        @JoinColumn(name = "property_flip_id", nullable = false, updatable = false) }, 
        inverseJoinColumns = { 
            @JoinColumn(name = "contractory_id", nullable = false, updatable = false) })
    private Collection<Contractor> contractors;
    @OneToMany(mappedBy = "propertyFlip", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="document_id")
    private Collection<Document> documents;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id")
    private Bank bank;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "property_flip_estate_agent", joinColumns = { 
        @JoinColumn(name = "property_flip_id", nullable = false, updatable = false) }, 
        inverseJoinColumns = { 
            @JoinColumn(name = "estate_agent_id", nullable = false, updatable = false) })
    private Collection<EstateAgent> estateAgents;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private Seller seller;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "purchaser_id")
    private Purchaser purchaser;
//    private PropertyFlipStatus propertyFlipStatus;

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTitleDeedNumber() {
        return titleDeedNumber;
    }

    public void setTitleDeedNumber(String titleDeedNumber) {
        this.titleDeedNumber = titleDeedNumber;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Collection<Note> getNotes() {
        return notes;
    }

    public void setNote(Collection<Note> notes) {
        this.notes = notes;
    }

    public Collection<Attorney> getAttorneys() {
        return attorneys;
    }

    public void setAttorneys(Collection<Attorney> attorneys) {
        this.attorneys = attorneys;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public Collection<Contractor> getContractors() {
        return contractors;
    }

    public void setContractors(Collection<Contractor> contractors) {
        this.contractors = contractors;
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Collection<EstateAgent> getEstateAgents() {
        return estateAgents;
    }

    public void setEstateAgents(Collection<EstateAgent> estateAgents) {
        this.estateAgents = estateAgents;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Purchaser getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Purchaser purchaser) {
        this.purchaser = purchaser;
    }

    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (stringValidator.isNullOrEmpty(getReferenceNumber().toString())) {
            throw new EntityValidationException("A reference number must be provided");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getReferenceNumber() != null) {
            stringBuilder.append("\nReferenceNumber: ");
            stringBuilder.append(getReferenceNumber());
        }
        if (getTitleDeedNumber() != null) {
            stringBuilder.append("\nTitleDeedNumber: ");
            stringBuilder.append(getTitleDeedNumber());
        }
        if (getCaseNumber() != null) {
            stringBuilder.append("\nCaseNumber: ");
            stringBuilder.append(getCaseNumber());
        }
        if (getSellingPrice() != null) {
            BigDecimalFormatter formatter = new BigDecimalDataFormatter();
            stringBuilder.append("\nSelling Price: ");
            stringBuilder.append(formatter.convertBigDecimalAsCurrency(
                getSellingPrice()));
        }
        if (getPurchasePrice() != null) {
            BigDecimalFormatter formatter = new BigDecimalDataFormatter();
            stringBuilder.append("\nPurchase Price: ");
            stringBuilder.append(formatter.convertBigDecimalAsCurrency(
                getPurchasePrice()));
        }
        if (getMilestone() != null) {
            stringBuilder.append("\nMilestone: ");
            stringBuilder.append(getMilestone().getId());
        }
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}