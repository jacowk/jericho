package za.co.jericho.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.LogManager;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.PrincipalToUserConversionException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.exception.ServiceBeanValidationException;
import za.co.jericho.security.PermissionGroups;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.domain.Groups;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.encryption.HashGenerator;
import za.co.jericho.security.encryption.SHA256HashGenerator;
import za.co.jericho.security.search.UserSearchCriteria;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

@Stateless
@Remote(ManageSecurityUserService.class)
public class ManageSecurityUserServiceBean extends AbstractServiceBean 
implements ManageSecurityUserService {

    /* TODO add permissions and audit trailing annotations */
    @Override
    public User addUser(User user) {
        /* Validations */
        /* State validation */
        user.validate();
        /* Validate unique properties */
        validateUniqueUserProperties(user);
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(user)) {
            /* Encrypt password */
            HashGenerator hashGenerator = new SHA256HashGenerator();
            user.setPassword(hashGenerator.generateHash(user.getPassword()));
            getEntityManager().persist(user);
            /* Persist group */
            Groups groups = new Groups();
            groups.setUsername(user.getUsername());
            groups.setGroupname(PermissionGroups.JERICHO.getValue());
            groups.setCreateDate(user.getCreateDate());
            groups.setCreatedBy(user.getCreatedBy());
            getEntityManager().persist(groups);
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        /* Validations */
        /* State validation */
        user.validate();
        /* Validate unique properties */
//        validateUniqueUserProperties(user);
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(user)) {
            //Validate that the user is not a duplicate
            //Validate that key properties are populated
            getEntityManager().merge(user);
        }
        return user;
    }

    @Override
    public User markUserDeleted(User user) {
        throw new DeleteNotSupportedException("Deleting a user is not supported");
    }

    @Override
    public List<User> searchUsers(UserSearchCriteria userSearchCriteria) {
        List<User> users = new ArrayList<>();
        if (userSearchCriteria != null) {
            /* Check permissions */
            if (!userSearchCriteria.isSearchForLogin()) {
                PermissionChecker permissionChecker = new UserPermissionChecker();
                permissionChecker.check(userSearchCriteria.getServiceUser(), ServiceName.SEARCH_USERS.getValue());
            }
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT u FROM User u ");
            searchUsersStringBuilder.append("WHERE u.firstname like :firstname ");
            searchUsersStringBuilder.append("AND u.surname like :surname ");
            searchUsersStringBuilder.append("AND u.email like :email ");
            searchUsersStringBuilder.append("AND u.username like :username ");
//            searchUsersStringBuilder.append("AND u.deleted IS FALSE;");
            String firstname = stringConvertor.convertForDatabaseSearch(userSearchCriteria.getFirstname(), userSearchCriteria.getSearchType());
            String surname = stringConvertor.convertForDatabaseSearch(userSearchCriteria.getSurname(), userSearchCriteria.getSearchType());
            String email = stringConvertor.convertForDatabaseSearch(userSearchCriteria.getEmail(), userSearchCriteria.getSearchType());
            String username = stringConvertor.convertForDatabaseSearch(userSearchCriteria.getUsername(), userSearchCriteria.getSearchType());
            users = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("firstname", firstname)
                .setParameter("surname", surname)
                .setParameter("email", email)
                .setParameter("username", username)
                .getResultList();
            
            if (!userSearchCriteria.isSearchForLogin()) {
                /* Audit Activity Logging */
                /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
                AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
                manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                    (EntityName.SECURITY_USER, //String entityName
                    "searchUsers", //String serviceName
                    AuditActivityType.SEARCH, //String activityType
                    userSearchCriteria.toString(), //String description
                    userSearchCriteria.getServiceUser())); //User currentUser
            }
        }
        else {
            throw new ServiceBeanException("User search criteria not provided");
        }
        return users;
    }
    
    private void validateUniqueUserProperties(User user) {
        /* Validate for unique username */
        List<User> findByUsernameQueryResult = getEntityManager()
                .createNamedQuery("findUserByUsername")
                .setParameter("username", user.getUsername()).getResultList();
        if (findByUsernameQueryResult.size() > 0) {
            throw new ServiceBeanValidationException("Username already exists");
        }
        /* Validate for unique email */
        List<User> findByEmailQueryResult = getEntityManager()
                .createNamedQuery("findUserByEmail")
                .setParameter("email", user.getEmail()).getResultList();
        if (findByEmailQueryResult.size() > 0) {
            throw new ServiceBeanValidationException("Email already exists");
        }
    }
    
    @Override
    public User findUser(Object id) {
        return getEntityManager().find(User.class, id);
    }

    @Override
    public List<User> findAllUsers() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    @Override
    public List<User> findAllUsersNotDeleted(UserSearchCriteria userSearchCriteria) {
        List<User> users = new ArrayList<>();
        if (userSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT u FROM User u ");
//            searchUsersStringBuilder.append("WHERE u.deleted IS FALSE;");
            users = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .getResultList();
            
            /* This method is not audited, since it is a secondary search in the system */
        }
        else {
            throw new ServiceBeanException("User search criteria not provided");
        }
        return users;
    }
    
    /**
     * This method takes a principal name and find the applicable user object
     * https://blogs.oracle.com/chengfang/entry/do_s_and_don_ts
     * 
     * @param principal String
     * @return User
     */
    @Override
    public User convertPrincipalToUser(String principal) {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("PrincipalToUserConverter: convert: ")
            .append(principal)
            .toString());
        /* Find user, and place user on session */
        User convertedUser = null;
        Collection<User> users = new ArrayList<>();
        users = searchUsersByPrincipal(principal);
        if (users != null) {
            if (users.size() == 1) {
                convertedUser = users.iterator().next();
                Collection<Role> roles = convertedUser.getRoles();
                if (roles != null) {
                    for (Role role: roles) {
                        LogManager.getRootLogger().info(new StringBuilder()
                            .append("Role: ")
                            .append(role.getName())
                            .toString());
                    }
                }
                else {
                    LogManager.getRootLogger().info(new StringBuilder()
                        .append("No roles defined for user")
                        .toString());
                }
                    
                LogManager.getRootLogger().info(new StringBuilder()
                    .append("LoginFilter: storeUserOnSession: ")
                    .append("User found: ")
                    .append(convertedUser.getId())
                    .toString());
            }
            else if (users.size() > 1) {
                throw new PrincipalToUserConversionException("Multiple users found for principal " + principal);
            }
            else {
                throw new PrincipalToUserConversionException("Username not found: " + principal);
            }
        }
        else {
            throw new PrincipalToUserConversionException("User list retrieved at login is null for principal " + principal);
        }
        return convertedUser;
    }
    
    private List<User> searchUsersByPrincipal(String principal) {
        List<User> users = new ArrayList<>();
        /* Service logic */
        StringConvertor stringConvertor = new StringDataConvertor();
        StringBuilder searchUsersStringBuilder = new StringBuilder();
        searchUsersStringBuilder.append("SELECT u FROM User u ");
        searchUsersStringBuilder.append("WHERE u.username like :username ");
        principal = stringConvertor.convertForDatabaseSearch(principal, null);
        users = getEntityManager().createQuery(searchUsersStringBuilder.toString())
            .setParameter("username", principal)
            .getResultList();
        return users;
    }
    
}