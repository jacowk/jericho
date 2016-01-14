package za.co.jericho.contact.search;

import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.util.conversion.ObjectToStringConvertor;
import za.co.jericho.util.conversion.ObjectToStringDataConvertor;

public class ContactSearchCriteria extends AbstractSearchCriteria {

    private String firstname;
    private String surname;
    private String workEmail;
    private String personalEmail;
    private Long idNumber;
    private String passportNumber;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

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
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("ID: ");
            stringBuilder.append(getId());
        }
        if (getFirstname() != null) {
            stringBuilder.append("\nFirstname: ");
            stringBuilder.append(getFirstname());
        }
        if (getSurname() != null) {
            stringBuilder.append("\nSurname: ");
            stringBuilder.append(getSurname());
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
            stringBuilder.append("\nId Number: ");
            stringBuilder.append(getIdNumber());
        }
        if (getPassportNumber() != null) {
            stringBuilder.append("\nPassport Number: ");
            stringBuilder.append(getPassportNumber());
        }
        return stringBuilder.toString();
    }
    
}