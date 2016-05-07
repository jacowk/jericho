/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.session;

/**
 *
 * @author Jaco Koekemoer
 */
public enum SessionVariables {
    
    CURRENT_USER("currentUser"),
    ENTITY("entity"),
    PROPERTY("property"),
    PROPERTY_FLIP("propertyFlip");
    
    private String value;
    
    SessionVariables(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
