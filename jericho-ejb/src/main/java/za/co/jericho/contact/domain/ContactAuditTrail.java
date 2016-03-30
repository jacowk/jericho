package za.co.jericho.contact.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-03-28
 */
@Entity
@Table(name="contact_audit_trail", schema = "jericho")
public class ContactAuditTrail extends AbstractAuditTrailEntity {
    
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
    @Column(name = "marital_status")
    private Short maritalStatus; //TODO Sort this lookup out
    @Column(name = "tax_number")
    private String taxNumber;
    @Column(name = "sa_citizen")
    private boolean saCitizen;

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

    public Short getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(Short maritalStatus) {
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
    
}
