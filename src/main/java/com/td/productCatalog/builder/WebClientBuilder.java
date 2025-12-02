package com.td.productCatalog.builder;

import com.td.model.Product;
import com.td.productCatalog.exception.ClientErrorInvokingWebClientException;
import com.td.productCatalog.exception.ServerErrorInvokingWebClientException;
import com.td.productCatalog.exception.TImeOutInvokingWebClientException;
import com.td.productCatalog.exception.UnexpectedErrorInvokingWebClientException;
import io.netty.handler.timeout.ReadTimeoutException;
import jakarta.annotation.Nullable;
import lombok.Generated;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WebClientBuilder {

    private final WebClient client;

    public <T> T invokeWebClientCall(@Nullable String uri, Class<T> resultType, @Nullable HttpHeaders headers, HttpMethod method){
        return (T) this.invokeWebClient(uri, null, resultType, headers, method).block();
    }

    public <T> T invokeWebClientCall(@Nullable String uri, Object requestBody, Class<T> resultType, @Nullable HttpHeaders headers, HttpMethod method){
        return (T) this.invokeWebClient(uri, requestBody, resultType, headers, method).block();
    }

    private <T> Mono<T> invokeWebClient(String uri, Object requestBody, Class<T> resultType, HttpHeaders headers, HttpMethod method){
        return this.client.method(method).uri(Objects.isNull(uri) ? "" : uri, new Object[0]).headers((httpHeaders -> {
            if(Objects.nonNull(headers)){
                httpHeaders.addAll(headers);
            }
        })).body(Objects.nonNull(requestBody)? BodyInserters.fromValue(requestBody): BodyInserters.empty()).exchangeToMono(
                (response) -> this.handleResponse(response, resultType)
        ).onErrorMap(WebClientRequestException.class,(throwable) -> this.handleRequestError(throwable));
    }


    public <T> T invokeWebClientCallforList(@Nullable String uri, Object requestBody, @Nullable HttpHeaders headers, HttpMethod method){
        return (T) this.invokeWebClientForList(uri, null, headers, method).block();
    }



    public Mono<List<Product>> invokeWebClientForList(String uri, Object requestBody, HttpHeaders headers, HttpMethod method) {
            ParameterizedTypeReference<List<Product>> typeRef = new ParameterizedTypeReference<>() {};
        return this.client.method(method)
                .uri(Objects.isNull(uri) ? "" : uri)
                .headers(httpHeaders -> {
                    if (Objects.nonNull(headers)) {
                        httpHeaders.addAll(headers);
                    }
                })
                .body(Objects.nonNull(requestBody) ? BodyInserters.fromValue(requestBody) : BodyInserters.empty())
                .exchangeToMono(response -> {
                    URI requestUri = response.request().getURI();
                    HttpHeaders respHeaders = response.headers().asHttpHeaders();

                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(typeRef);
                    } else if (response.statusCode().is4xxClientError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new ClientErrorInvokingWebClientException(
                                        requestUri,
                                        String.format("Client error Exception thrown while using webclient. HttpStatus code of '%s'.", response.statusCode()),
                                        response.statusCode().value(),
                                        response.statusCode().toString(),
                                        respHeaders,
                                        Optional.ofNullable(errorBody).map(String::getBytes).orElse(null)
                                )));
                    } else if (response.statusCode().is5xxServerError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new ServerErrorInvokingWebClientException(
                                        requestUri,
                                        String.format("Server error Exception thrown while using webclient. HttpStatus code of '%s'.", response.statusCode()),
                                        response.statusCode().value(),
                                        response.statusCode().toString(),
                                        respHeaders,
                                        Optional.ofNullable(errorBody).map(String::getBytes).orElse(null)
                                )));
                    } else {
                        return Mono.error(new UnexpectedErrorInvokingWebClientException(requestUri, "Unexpected error exception thrown while using webclient"));
                    }
                })
                .onErrorMap(WebClientRequestException.class, this::handleRequestError);
    }


    private <T> Mono<T> handleResponse(ClientResponse response, Class<T> resultType) {
        HttpStatus status = HttpStatus.valueOf(response.statusCode().value());
        URI requestUri = response.request().getURI();
        HttpHeaders headers = response.headers().asHttpHeaders();
        if(response.statusCode().is2xxSuccessful()){
            return response.bodyToMono(resultType);
        }
        else if (response.statusCode().is4xxClientError()){
            return response.bodyToMono(String.class).flatMap((errorBody) -> Mono.error(new ClientErrorInvokingWebClientException(requestUri, String.format("Client error Exception thrown while using webclient. HttpStatus code of '%s'.", status),status.value(), status.toString(), headers, (byte[])Optional.ofNullable(errorBody).map(String :: getBytes).orElse(null))));
        }
        else{
            return response.statusCode().is5xxServerError() ? response.bodyToMono(String.class).flatMap((errorbody) -> Mono.error(new ServerErrorInvokingWebClientException(requestUri, String.format("Server error Exception thrown while using webclient. HttpStatus code of '%s'.",status), status.value(), status.toString(), headers, Optional.ofNullable(errorbody).map(String::getBytes).orElse(null)))) : Mono.error(new UnexpectedErrorInvokingWebClientException(requestUri,"Unexpected error exception thrown while using webclient"));
        }

    }

    private Throwable handleRequestError(WebClientRequestException exception) {
        URI requestUri = exception.getUri();
        if(exception.getCause() instanceof ReadTimeoutException){
            return new TImeOutInvokingWebClientException(requestUri, "Read Timeout exception thrown while using webclient");
        }
        else {
            return exception.getCause() instanceof ConnectException ? new TImeOutInvokingWebClientException(requestUri, "Connection Timeout exception thrown while using WebClient") : new UnexpectedErrorInvokingWebClientException(requestUri, "Unexpected error exception thrown while using Webclient");
        }
    }

    @Generated
    WebClientBuilder (WebClient client) {
        this.client = client;
    }

    @Generated
    public static WebClientBuilderBuilder builder(){
       return new WebClientBuilderBuilder();
    }

    @Generated
    public static class WebClientBuilderBuilder{
        @Generated
        private WebClient client;
        @Generated
        WebClientBuilderBuilder(){

        }
        @Generated
        public WebClientBuilderBuilder client(WebClient client){
            this.client =client;
            return this;
        }
        @Generated
        public WebClientBuilder build(){
            return new WebClientBuilder(this.client);
        }
        @Generated
        public String toString(){
            return "WebClientBuilder.WebClientBuilderBuilder(client=" + String.valueOf(this.client) + ")";
        }

    }

}
