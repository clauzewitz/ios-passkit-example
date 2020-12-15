package com.example.demo.handler.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private final int status = HttpStatus.BAD_REQUEST.value();

    public BadRequestException() {
        super("Invalid Parameter");
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    protected BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }
}
