package com.td.productCatalog.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GenericException{

    public UnauthorizedException(String message){
        super(message, HttpStatus.FORBIDDEN, (String) null);
    }
}
