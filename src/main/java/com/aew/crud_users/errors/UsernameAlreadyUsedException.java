package com.aew.crud_users.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Indica que un username ya esta en uso.
 * 
 * @author Adrian E. Wilgenhoff
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyUsedException extends Exception {

    private static final long serialVersionUID = -2840245819784283820L;

    public UsernameAlreadyUsedException(String message) {
        super(message);

    }

}