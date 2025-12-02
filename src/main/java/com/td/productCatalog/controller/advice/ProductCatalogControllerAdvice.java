package com.td.productCatalog.controller.advice;


import com.td.productCatalog.exception.InvokingWebClientException;
import com.td.productCatalog.exception.TImeOutInvokingWebClientException;
import com.td.productCatalog.model.ApiErrorResponse;
import com.td.productCatalog.model.ProblemDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ProductCatalogControllerAdvice {

    @ExceptionHandler(TImeOutInvokingWebClientException.class)
    public ResponseEntity<ApiErrorResponse> handleTimeOutException(InvokingWebClientException exception){

        HttpStatus httpStatus = (HttpStatus) exception.getStatusCode();
        URI uri = exception.getUri();
        List<ProblemDetail> problemDetailList;

        problemDetailList = Collections.singletonList(createProblemDetail(httpStatus,exception.getMessage(),
                exception.getClass().getSimpleName(), URI.create(uri.getPath())));

        return handleAndLogExcetion(exception,httpStatus, problemDetailList);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception exception){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        URI uri = URI.create("");
        List<ProblemDetail> problemDetailList;
        problemDetailList = Collections.singletonList(createProblemDetail(httpStatus,exception.getMessage(),exception.getClass().getSimpleName(),uri));
        return handleAndLogExcetion(exception,httpStatus, problemDetailList);
    }

    private ProblemDetail createProblemDetail(HttpStatus httpStatus, String errorDetail,
                                             String title, URI instance){
        ProblemDetail problemDetail = new ProblemDetail();
        problemDetail.setType(title);
        problemDetail.setStatus(httpStatus.value());
        problemDetail.setDetail(errorDetail);
        problemDetail.setInstance(String.valueOf(instance));
        return problemDetail;
    }

    private ResponseEntity<ApiErrorResponse> handleAndLogExcetion(Exception exception, HttpStatus httpStatus , List<ProblemDetail> problemDetailList) {
        return  ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiErrorResponse.builder().type(exception.getMessage()).detail(problemDetailList).build());
    }


}
