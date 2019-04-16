package com.aew.crud_users.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Informa que un usuario no ha sido encontrado en la base de datos. Retorna un
 * Not Found code.
 * 
 * @author Adrian E. Wilgenhoff
 * 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = -8675491583677452451L;

    public UserNotFoundException(String message) {
        super(message);

    }

}
