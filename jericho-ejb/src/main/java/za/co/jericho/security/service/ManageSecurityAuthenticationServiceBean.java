package za.co.jericho.security.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.common.service.*;
import za.co.jericho.security.domain.*;

@Stateless
@Remote(ManageSecurityAuthenticationService.class)
public class ManageSecurityAuthenticationServiceBean extends AbstractServiceBean 
implements ManageSecurityAuthenticationService {
    
    @Override
    public boolean login(User user) {
        return true;
    }

    @Override
    public boolean logout(User user) {
        return true;
    }

}