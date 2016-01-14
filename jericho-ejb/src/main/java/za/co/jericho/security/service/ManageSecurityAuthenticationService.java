package za.co.jericho.security.service;

import javax.ejb.Remote;
import za.co.jericho.security.domain.User;

/**
 *
 * @author user
 */
@Remote
public interface ManageSecurityAuthenticationService {
    
    public boolean login(User user);

    public boolean logout(User user);
    
}
