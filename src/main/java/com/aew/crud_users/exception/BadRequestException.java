package com.aew.crud_users.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Simple exception with a message, that returns an Bad Request code. Informa
 * que ha habido una mala entrada de datos.
 * 
 * @author Adrian E. Wilgenhoff
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

    private static final long serialVersionUID = 7980625658990973035L;

    public BadRequestException() {
        super("Bad entry of data");

    }

}