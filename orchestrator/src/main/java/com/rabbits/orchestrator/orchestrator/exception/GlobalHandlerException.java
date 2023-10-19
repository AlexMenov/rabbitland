package com.rabbits.orchestrator.orchestrator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {
    private static ErrorMessageException createExceptionResponseEntity(RuntimeException exception) {
        return new ErrorMessageException(exception.getMessage());
    }

    @ExceptionHandler({JobIdIsNotValidException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageException handleJobIdIsNotValidException(JobIdIsNotValidException exception) {
        return createExceptionResponseEntity(exception);
    }

    @ExceptionHandler({JobRequestIllegalException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageException handleJobRequestIllegalException(JobRequestIllegalException exception) {
        return createExceptionResponseEntity(exception);
    }

    @ExceptionHandler({JobResponseNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageException handleJobResponseNotFoundException(JobResponseNotFoundException exception) {
        return createExceptionResponseEntity(exception);
    }
}
