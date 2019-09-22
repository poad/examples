package com.github.poad.examples.java.webapps.handler;

import com.github.poad.examples.java.webapps.exception.RestApiException;
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

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class ApplicationErrorHandler  extends ResponseEntityExceptionHandler {
    @Value("${debug:false}")
    boolean debug;

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleException(RestApiException exception, WebRequest request) {
        HttpStatus status = exception.getStatus()== null ?
                HttpStatus.INTERNAL_SERVER_ERROR : exception.getStatus();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("message", exception.getMessage());
        return handleExceptionInternal(exception, body, headers, status, request);
    }

}
