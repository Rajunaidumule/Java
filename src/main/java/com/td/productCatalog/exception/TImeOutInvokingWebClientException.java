package com.td.productCatalog.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

public class TImeOutInvokingWebClientException extends InvokingWebClientException{

    public TImeOutInvokingWebClientException(URI uri, String message){
        super(uri, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
