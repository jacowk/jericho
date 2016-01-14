package za.co.jericho.security.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import za.co.jericho.security.domain.Role;
import za.co.jericho.security.service.ManageSecurityRoleService;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-08
 */
public class RoleConverter implements Converter {

    @EJB
    private ManageSecurityRoleService manageSecurityRoleService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, 
        String value) {
        return manageSecurityRoleService.searchConverterRole((String) value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, 
        Object value) {
        return ((Role) value).getName();
    }
    
}
