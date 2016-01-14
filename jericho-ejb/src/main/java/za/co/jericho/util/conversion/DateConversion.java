/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.util.conversion;

import java.util.Date;

/**
 *
 * @author Jaco Koekemoer
 */
public interface DateConversion {
    
    public String getStringDateFromDate(Date date, String format);
    
}
