package za.co.jericho.propertyflip.search;

import za.co.jericho.common.search.AbstractSearchCriteria;

public class PropertyFlipSearchCriteria extends AbstractSearchCriteria {
    
    private Long referenceNumber;
    private String titleDeedNumber;
    private String caseNumber;

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
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getReferenceNumber() != null) {
            stringBuilder.append("|ReferenceNumber: ");
            stringBuilder.append(getReferenceNumber());
        }
        if (getTitleDeedNumber() != null) {
            stringBuilder.append("|TitleDeedNumber: ");
            stringBuilder.append(getTitleDeedNumber());
        }
        if (getCaseNumber() != null) {
            stringBuilder.append("|CaseNumber: ");
            stringBuilder.append(getCaseNumber());
        }
        return stringBuilder.toString();
    }
    
}