package com.danielarrais.orderservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHandleException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductsOutOfStockException.class)
    public ResponseEntity<?> handleIllegalArgumentException(ProductsOutOfStockException e, WebRequest request) {
        Problem problem = Problem.builder()
                .status(HttpStatus.BAD_REQUEST)
                .title("Problems with stock")
                .detail(e.getMessage())
                .build();

        e.printStackTrace();

        return super.handleExceptionInternal(e, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
