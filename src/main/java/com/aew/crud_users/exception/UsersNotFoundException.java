package com.aew.crud_users.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Informa que no hay ningun usuario registrado en la base de datos. Retorna un
 * No Content code.
 * 
 * @author Adrian E. Wilgenhoff
 * 
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class UsersNotFoundException extends Exception {

    private static final long serialVersionUID = -8675491583677452451L;

    public UsersNotFoundException() {
        super("There are not any user registered");

    }

}
