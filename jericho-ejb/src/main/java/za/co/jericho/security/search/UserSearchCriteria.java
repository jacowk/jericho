package za.co.jericho.security.search;

import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.security.domain.Role;

public class UserSearchCriteria extends AbstractSearchCriteria {
    
    private String firstname = "";
    private String surname = "";
    private String email = "";
    private String username = "";
    private boolean searchForLogin = false;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSearchForLogin() {
        return searchForLogin;
    }

    public void setSearchForLogin(boolean searchForLogin) {
        this.searchForLogin = searchForLogin;
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
        if (getEmail() != null) {
            stringBuilder.append("\nEmail: ");
            stringBuilder.append(getEmail());
        }
        if (getUsername() != null) {
            stringBuilder.append("\nUsername: ");
            stringBuilder.append(getUsername());
        }
        return stringBuilder.toString();
    }

}