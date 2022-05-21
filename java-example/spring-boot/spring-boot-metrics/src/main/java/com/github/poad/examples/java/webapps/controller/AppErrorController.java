package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.RestAppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class AppErrorController extends ResponseEntityExceptionHandler {
    @Value("${debug:false}")
    boolean debug;

    @ExceptionHandler(RestAppException.class)
    public ResponseEntity<Object> handleException(RestAppException exception, WebRequest request) {
        HttpStatus status  = HttpStatus.resolve(exception.getStatus()) == null ?
                HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.resolve(exception.getStatus());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("message", exception.getMessage());
        assert status != null;
        return handleExceptionInternal(exception, body, headers, status, request);
    }
}
