package za.co.jericho.security.service;

import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.UserSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
@Remote
public interface ManageSecurityUserService {
 
    public User addUser(User user);
    
    public User updateUser(User user);
    
    public User markUserDeleted(User user);
    
    public Collection<User> searchUsers(UserSearchCriteria userSearchCriteria);
    
    public User findUser(Object id);

    public Collection<User> findAllUsers();
    
    public Collection<User> findAllUsersNotDeleted(UserSearchCriteria userSearchCriteria);
    
    public User convertPrincipalToUser(String principal);
    
}
