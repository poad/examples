package com.github.poad.examples.java.webapps.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Exception cause) {
        super(message, cause);
    }

    public NotFoundException(Exception cause) {
        super(cause);
    }
}
