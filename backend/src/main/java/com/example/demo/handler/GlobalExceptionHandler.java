package com.example.demo.handler;

import com.example.demo.handler.exception.BadRequestException;
import com.example.demo.handler.exception.NotExsistException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CustomError commonException(HttpServletRequest request, Exception exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public CustomError badRequestException(HttpServletRequest request, BadRequestException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(exception.getStatus())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotExsistException.class)
    public CustomError notExsistException(HttpServletRequest request, NotExsistException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(exception.getStatus())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }
}
