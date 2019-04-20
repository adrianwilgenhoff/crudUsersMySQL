package com.aew.crud_users.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {

        this.messageSource = messageSource;
    }

    @ExceptionHandler(TelephoneAlreadyUsedExecption.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleTelephoneAlreadyUsedExecption(TelephoneAlreadyUsedExecption ex) {

        System.out.println("se prendio fuego todo");

    }

}