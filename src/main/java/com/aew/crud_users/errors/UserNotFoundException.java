package com.aew.crud_users.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Exception para representar que un usuario no ha sido encontrado.
 * 
 * @author Adrian E. Wilgenhoff
 * 
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = -8675491583677452451L;

    public UserNotFoundException(String message) {
        super(message);

    }

}
