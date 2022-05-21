package com.github.poad.example.springboot.jpa.exception;

public class RestAppException extends RuntimeException {
    private final int status;

    public RestAppException(int status) {
        super();
        this.status = status;
    }

    public RestAppException(int status, String message) {
        super(message);
        this.status = status;
    }

    public RestAppException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
