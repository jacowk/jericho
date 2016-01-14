package za.co.jericho.security.domain;

import java.util.ArrayList;
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
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.exception.EntityValidationException;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Entity
@Table(name="users", schema="jericho")
@NamedQueries({
    @NamedQuery(name = "findUserByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "findUserByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User extends AbstractEntity {

    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
//	private Collection<Diary> diaries = new ArrayList<Diary>();
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", 
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private Collection<AuditActivity> auditActivities = new ArrayList<>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    
    public void addRole(Role role) {
        this.roles.add(role);
    }
    
    public void addRoleCollection(Collection<Role> roles) {
        for (Role role: roles) {
            this.roles.add(role);
        }
    }
    
    @Override
    public void validate() {
        StringValidator stringValidator = new StringDataValidator();
        if (stringValidator.isNullOrEmpty(getFirstname())) {
            throw new EntityValidationException("Firstname not provided");
        }
        if (stringValidator.isNullOrEmpty(getSurname())) {
            throw new EntityValidationException("Surname not provided");
        }
        if (stringValidator.isNullOrEmpty(getEmail())) {
            throw new EntityValidationException("Email not provided");
        }
        if (stringValidator.isNullOrEmpty(getUsername())) {
            throw new EntityValidationException("Username not provided");
        }
        if (stringValidator.isNullOrEmpty(getPassword())) {
            throw new EntityValidationException("Password not provided");
        }
        if (getRoles() == null || getRoles().isEmpty()) {
            throw new EntityValidationException("Role not provided");
        }
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
        if (getPassword() != null) {
            stringBuilder.append("\nPassword: ");
            stringBuilder.append(getPassword());
        }
        if (getRoles() != null && getRoles().size() > 0)
        {
            stringBuilder.append("\nRoles: ");
            for (Role role: getRoles())
            {
                stringBuilder.append("\n\t");
                stringBuilder.append(role.getName());
            }
        }
        return stringBuilder.toString();
    }
    
}