/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.security.service.permissioncheck;

import za.co.jericho.security.domain.User;

/**
 *
 * @author user
 */
public interface PermissionChecker {
    
    public boolean check(User user, String permissionName);
    
}
