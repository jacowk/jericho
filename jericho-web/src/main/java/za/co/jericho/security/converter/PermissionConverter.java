package za.co.jericho.security.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.service.ManageSecurityPermissionService;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-01-08
 */
public class PermissionConverter implements Converter {

    @EJB
    private ManageSecurityPermissionService manageSecurityPermissionService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, 
        String value) {
        return manageSecurityPermissionService.searchConverterPermission((String) 
            value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, 
        Object value) {
        return ((Permission) value).getName();
    }
    
}
