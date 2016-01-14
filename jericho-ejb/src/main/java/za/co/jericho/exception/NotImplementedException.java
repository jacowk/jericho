/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.exception;

/**
 *
 * @author Jaco Koekemoer
 */
public class NotImplementedException extends RuntimeException {
    
    public NotImplementedException() {
        super();
    }
    
    public NotImplementedException(String message) {
        super(message);
    }
    
}
