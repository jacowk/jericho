package za.co.jericho.propertyflip.domain;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Entity
@Table(name="milestone")
public class Milestone extends AbstractEntity {

    @Temporal(TemporalType.DATE)
    @Column(name = "date_offer_made")
    private Date dateOfferMade;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_acceptance")
    private Date dateOfAcceptance;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_seller_signature")
    private Date dateOfSellerSignature;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_purchaser_signature")
    private Date dateOfPurchaserSignature;
    @Column(name = "finance_status")
    private Long financeStatus;
    @Temporal(TemporalType.DATE)
    @Column(name = "renovation_start_date")
    private Date renovationStartDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "renovation_end_date")
    private Date renovationEndDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "for_sale_date")
    private Date forSaleDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "sell_date")
    private Date sellDate;
    @OneToOne(mappedBy="milestone")
    private PropertyFlip propertyFlip;

    public Date getDateOfferMade() {
        return this.dateOfferMade;
    }

    public void setDateOfferMade(Date dateOfferMade) {
        this.dateOfferMade = dateOfferMade;
    }

    public Date getDateOfAcceptance() {
        return this.dateOfAcceptance;
    }

    public void setDateOfAcceptance(Date dateOfAcceptance) {
        this.dateOfAcceptance = dateOfAcceptance;
    }

    public Date getDateOfSellerSignature() {
        return this.dateOfSellerSignature;
    }

    public void setDateOfSellerSignature(Date dateOfSellerSignature) {
        this.dateOfSellerSignature = dateOfSellerSignature;
    }

    public Date getDateOfPurchaserSignature() {
        return this.dateOfPurchaserSignature;
    }

    public void setDateOfPurchaserSignature(Date dateOfPurchaserSignature) {
        this.dateOfPurchaserSignature = dateOfPurchaserSignature;
    }

    public Long getFinanceStatus() {
        return this.financeStatus;
    }

    public void setFinanceStatus(Long financeStatus) {
        this.financeStatus = financeStatus;
    }

    public Date getRenovationStartDate() {
        return this.renovationStartDate;
    }

    public void setRenovationStartDate(Date renovationStartDate) {
        this.renovationStartDate = renovationStartDate;
    }

    public Date getRenovationEndDate() {
        return this.renovationEndDate;
    }

    public void setRenovationEndDate(Date renovationEndDate) {
        this.renovationEndDate = renovationEndDate;
    }

    public Date getForSaleDate() {
        return this.forSaleDate;
    }

    public void setForSaleDate(Date forSaleDate) {
        this.forSaleDate = forSaleDate;
    }

    public Date getSellDate() {
        return this.sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    @Override
    public void validate() {
        if (propertyFlip == null) {
            throw new EntityValidationException("Milestone must have a property flip");
        }
    }
    
    @Override
    public String toString() {
        //TODO: Format these dates with DateDataConversion
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("|ID: ");
            stringBuilder.append(getId());
        }
        if (getDateOfferMade() != null) {
            stringBuilder.append("|DateOfferMade: ");
            stringBuilder.append(getDateOfferMade());
        }
        if (getDateOfAcceptance() != null) {
            stringBuilder.append("|DateOfAcceptance: ");
            stringBuilder.append(getDateOfAcceptance());
        }
        if (getDateOfSellerSignature() != null) {
            stringBuilder.append("|DateOfSellerSignature: ");
            stringBuilder.append(getDateOfSellerSignature());
        }
        if (getDateOfPurchaserSignature() != null) {
            stringBuilder.append("|DateOfPurchaserSignature: ");
            stringBuilder.append(getDateOfPurchaserSignature());
        }
        if (getFinanceStatus() != null) {
            stringBuilder.append("|FinanceStatus: ");
            stringBuilder.append(getFinanceStatus());
        }
        if (getRenovationStartDate() != null) {
            stringBuilder.append("|RenovationStartDate: ");
            stringBuilder.append(getRenovationStartDate());
        }
        if (getRenovationEndDate() != null) {
            stringBuilder.append("|RenovationEndDate: ");
            stringBuilder.append(getRenovationEndDate());
        }
        if (getForSaleDate() != null) {
            stringBuilder.append("|ForSaleDate: ");
            stringBuilder.append(getForSaleDate());
        }
        if (getSellDate() != null) {
            stringBuilder.append("|SellDate: ");
            stringBuilder.append(getSellDate());
        }
        if (getPropertyFlip() != null) {
            stringBuilder.append("|PropertyFlip: ");
            stringBuilder.append(getPropertyFlip().getId());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}