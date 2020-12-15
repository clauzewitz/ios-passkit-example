package com.example.demo.handler.exception;

import org.springframework.http.HttpStatus;

public class NotExsistException extends RuntimeException {
    private final int status = HttpStatus.NOT_FOUND.value();

    public NotExsistException() {
        super("Not exsist Content");
    }

    public NotExsistException(String message) {
        super(message);
    }

    public NotExsistException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExsistException(Throwable cause) {
        super(cause);
    }

    protected NotExsistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }
}
