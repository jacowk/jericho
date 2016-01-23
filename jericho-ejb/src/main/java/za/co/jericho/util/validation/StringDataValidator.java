/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.util.validation;

/**
 *
 * @author user
 */
public class StringDataValidator implements StringValidator {

    @Override
    public boolean isNull(String data) {
        return data == null || data.equals("null");
    }

    @Override
    public boolean isNullOrEmpty(String data) {
        if (!isNull(data)) {
            return data.length() == 0;
        }
        else {
            return true;
        }
    }
    
    @Override
    public boolean isNumeric(String data) {
        try {
            Long.parseLong(data);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
}
