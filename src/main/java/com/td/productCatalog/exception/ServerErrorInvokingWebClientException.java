package com.td.productCatalog.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.net.URI;

public class ServerErrorInvokingWebClientException extends InvokingWebClientException {


    public ServerErrorInvokingWebClientException(URI uri, String message, int statusCode, String statusText, @Nullable HttpHeaders headers, @Nullable byte[] responseBody) {
        super(uri, message, statusCode, statusText, headers, responseBody);
    }
}
