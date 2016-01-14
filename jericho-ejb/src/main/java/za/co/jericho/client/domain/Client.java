package za.co.jericho.client.domain;

import za.co.jericho.client.lookup.MaritalStatus;
import za.co.jericho.contact.domain.Contact;

/**
 * 
 * @author user
 * @deprecated Use seller or purchaser
 */
public class Client {

    private Long idNumber;
    private String passportNumber;
    private MaritalStatus maritalStatus;
    private String taxNumber;
    private boolean saCitizen;
    private Contact contact;

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public boolean isSaCitizen() {
        return saCitizen;
    }

    public void setSaCitizen(boolean saCitizen) {
        this.saCitizen = saCitizen;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
}