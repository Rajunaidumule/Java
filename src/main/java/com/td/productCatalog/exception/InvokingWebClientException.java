package com.td.productCatalog.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;

import lombok.Generated;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InvokingWebClientException extends RuntimeException{

    private final URI uri;
    private final HttpStatusCode statusCode;
    private final String statusText;
    private final byte[] responseBody;
    private final HttpHeaders headers;

    public InvokingWebClientException (URI uri, String message, int statusCode, String statusText, @Nullable HttpHeaders headers,
                                       @Nullable byte[] responseBody){
        super(message);
        this.uri = uri;
        this.statusCode = HttpStatusCode.valueOf(statusCode);
        this.statusText = statusText;
        this.headers = copy(headers);
        this.responseBody = Optional.ofNullable(responseBody).isPresent() ? responseBody : new byte[0];
    }

    public InvokingWebClientException(URI uri, String message, int statusCode) {
        super(message);
        this.uri = uri;
        this.statusCode = HttpStatusCode.valueOf(statusCode);
        this.statusText= null;
        this.headers = null;
        this.responseBody = null;
    }

    private static HttpHeaders copy(@Nullable HttpHeaders headers){
        if(Objects.isNull(headers)){
            return HttpHeaders.EMPTY;
        }
        else{
            HttpHeaders result = new HttpHeaders();
            for(Map.Entry<String, List<String>> entry : headers.entrySet()){
                for (String value :  entry.getValue()){
                    result.add(entry.getKey(), value);
                }
            }
            return result;
        }
    }

    public String getResponseBodyAsString(){
        return (String) Optional.ofNullable(this.responseBody).map((bytes) ->  new String (bytes, StandardCharsets.UTF_8)).orElse(null);
    }

    @Generated
    public URI getUri() {
        return uri;
    }
    @Generated
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
    @Generated
    public String getStatusText() {
        return statusText;
    }
    @Generated
    public byte[] getResponseBody() {
        return responseBody;
    }
    @Generated
    public HttpHeaders getHeaders() {
        return headers;
    }
}
