package com.td.productCatalog.exception;

import lombok.Generated;
import org.springframework.http.HttpStatusCode;

public class GenericException extends RuntimeException {

    private final HttpStatusCode statusCode;
    private final String instance;

    public GenericException (String message, HttpStatusCode statusCode, String instance){
        super(message);
        this.instance = instance;
        this.statusCode = statusCode;
    }

    @Generated
    public HttpStatusCode getStatusCode() {
        return this.statusCode;
    }
    @Generated
    public String getInstance() {
        return this.instance;
    }
}
