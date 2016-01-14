/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.jericho.exception;

/**
 *
 * @author user
 */
public class EntityValidatorException extends RuntimeException {
    
    public EntityValidatorException(String message) {
        super(message);
    }
    
}
