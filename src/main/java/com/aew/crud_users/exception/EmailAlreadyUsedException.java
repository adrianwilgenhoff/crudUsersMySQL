package com.aew.crud_users.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Informa que un email ya esta en uso. Retorna un Conflict code.
 * 
 * @author Adrian E. Wilgenhoff
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyUsedException extends Exception {

    private static final long serialVersionUID = -445598068086002523L;

    public EmailAlreadyUsedException() {
        super("Email is already in use");

    }

}