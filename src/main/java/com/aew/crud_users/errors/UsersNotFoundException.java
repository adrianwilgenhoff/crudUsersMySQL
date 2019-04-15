package com.aew.crud_users.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Exception para representar que no hay ningun usuario registrado en la base de
 * datos.
 * 
 * @author Adrian E. Wilgenhoff
 * 
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class UsersNotFoundException extends Exception {

    private static final long serialVersionUID = -8675491583677452451L;

    public UsersNotFoundException(String message) {
        super(message);

    }

}
