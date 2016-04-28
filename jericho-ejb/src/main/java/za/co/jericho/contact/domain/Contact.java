package za.co.jericho.contact.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.contact.lookup.MaritalStatus;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Entity
@Table(name="contact", schema = "jericho")
public class Contact extends AbstractEntity {

    @Column(name = "title", length = 10)
    private String title;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "surname")
    private String surname;
    @Column(name = "home_tel_number")
    private String homeTelNumber;
    @Column(name = "work_tel_number")
    private String workTelNumber;
    @Column(name = "cell_number")
    private String cellNumber;
    @Column(name = "fax_number")
    private String faxNumber;
    @Column(name = "work_email")
    private String workEmail;
    @Column(name = "personal_email")
    private String personalEmail;
    @Column(name = "id_number")
    private Long idNumber;
    @Column(name = "passport_number")
    private String passportNumber;
    @OneToOne
    @JoinColumn(name="marital_status_id")
    private MaritalStatus maritalStatus;
    @Column(name = "tax_number")
    private String taxNumber;
    @Column(name = "sa_citizen")
    private boolean saCitizen = true;
//    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinColumn(name="contact_id")
//    private EstateAgent estateAgent;
//    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinColumn(name="contact_id")
//    private Attorney attorney;
//    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinColumn(name="contact_id")
//    private Contractor contractor;
//    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinColumn(name="contact_id")
//    private Purchaser purchaser;
//    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinColumn(name="contact_id")
//    private Seller seller;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getHomeTelNumber() {
        return this.homeTelNumber;
    }

    public void setHomeTelNumber(String homeTelNumber) {
        this.homeTelNumber = homeTelNumber;
    }

    public String getWorkTelNumber() {
        return this.workTelNumber;
    }

    public void setWorkTelNumber(String workTelNumber) {
            this.workTelNumber = workTelNumber;
    }

    public String getCellNumber() {
        return this.cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getFaxNumber() {
        return this.faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getWorkEmail() {
        return this.workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getPersonalEmail() {
        return this.personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public Long getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassportNumber() {
        return this.passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public boolean getSaCitizen() {
        return this.saCitizen;
    }

    public void setSaCitizen(boolean saCitizen) {
        this.saCitizen = saCitizen;
    }

    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (getFirstname() == null || stringValidator.isNullOrEmpty(getFirstname())) {
            throw new EntityValidationException("Firstname must be provided");
        }
        if (getSurname() == null || stringValidator.isNullOrEmpty(getSurname())) {
            throw new EntityValidationException("Surname must be provided");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getTitle() != null) {
            stringBuilder.append("\nTitle: ");
            stringBuilder.append(getTitle());
        }
        if (getFirstname() != null) {
            stringBuilder.append("\nFirstname: ");
            stringBuilder.append(getFirstname());
        }
        if (getSurname() != null) {
            stringBuilder.append("\nSurname: ");
            stringBuilder.append(getSurname());
        }
        if (getHomeTelNumber() != null) {
            stringBuilder.append("\nHome Tel No: ");
            stringBuilder.append(getHomeTelNumber());
        }
        if (getWorkTelNumber() != null) {
            stringBuilder.append("\nWork Tel No: ");
            stringBuilder.append(getWorkTelNumber());
        }
        if (getCellNumber() != null) {
            stringBuilder.append("\nCell No: ");
            stringBuilder.append(getCellNumber());
        }
        if (getFaxNumber() != null) {
            stringBuilder.append("\nFax No: ");
            stringBuilder.append(getFaxNumber());
        }
        if (getWorkEmail() != null) {
            stringBuilder.append("\nWork Email: ");
            stringBuilder.append(getWorkEmail());
        }
        if (getPersonalEmail() != null) {
            stringBuilder.append("\nPersonal Email: ");
            stringBuilder.append(getPersonalEmail());
        }
        if (getIdNumber() != null) {
            stringBuilder.append("\nID Number: ");
            stringBuilder.append(getIdNumber());
        }
        if (getPassportNumber() != null) {
            stringBuilder.append("\nPassport Number: ");
            stringBuilder.append(getPassportNumber());
        }
        if (getMaritalStatus() != null) {
            stringBuilder.append("\nMarital Status: ");
            stringBuilder.append(getMaritalStatus());
        }
        if (getTaxNumber() != null) {
            stringBuilder.append("\nTax Number: ");
            stringBuilder.append(getTaxNumber());
        }
        stringBuilder.append("\nSA Citizen: ");
        stringBuilder.append(getSaCitizen());
        stringBuilder.append("\nDeleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }

}