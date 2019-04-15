package com.aew.crud_users.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Indica que ha habido una mala mala entrada de datos.
 * 
 * @author Adrian E. Wilgenhoff
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

    private static final long serialVersionUID = 7980625658990973035L;

    public BadRequestException(String message) {
        super(message);

    }

}