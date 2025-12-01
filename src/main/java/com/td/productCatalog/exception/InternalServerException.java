package com.td.productCatalog.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends GenericException{

    public InternalServerException(String message){
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, (String) null);
    }
}
