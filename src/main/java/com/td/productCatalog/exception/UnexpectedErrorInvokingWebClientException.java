package com.td.productCatalog.exception;

import org.springframework.http.HttpStatus;
import java.net.URI;

public class UnexpectedErrorInvokingWebClientException extends InvokingWebClientException{
    public UnexpectedErrorInvokingWebClientException (URI uri, String message){
        super(uri, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
