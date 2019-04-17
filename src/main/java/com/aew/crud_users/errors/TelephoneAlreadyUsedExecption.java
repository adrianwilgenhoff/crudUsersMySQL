package com.aew.crud_users.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Indica que un telefono ya esta en uso.
 * 
 * @author Adrian E. Wilgenhoff
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class TelephoneAlreadyUsedExecption extends Exception {

    private static final long serialVersionUID = -7001177081389712932L;

    public TelephoneAlreadyUsedExecption() {
        super("Telephone number is already in use");

    }

}