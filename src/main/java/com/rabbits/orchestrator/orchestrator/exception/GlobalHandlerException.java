package com.rabbits.orchestrator.orchestrator.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageException handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ErrorMessageException(exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageException handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ErrorMessageException(exception.getMessage());
    }

    @ExceptionHandler({JobResponseNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageException handleJobResponseNotFoundException(JobResponseNotFoundException exception) {
        return new ErrorMessageException(exception.getMessage());
    }
}
