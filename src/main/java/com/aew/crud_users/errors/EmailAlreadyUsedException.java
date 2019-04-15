package com.aew.crud_users.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Indica que un email ya esta en uso.
 * 
 * @author Adrian E. Wilgenhoff
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyUsedException extends Exception {

    private static final long serialVersionUID = -445598068086002523L;

    public EmailAlreadyUsedException(String message) {
        super(message);

    }

}