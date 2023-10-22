package com.rabbits.orchestrator.orchestrator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler({JobIdIsNotValidException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorMessageException handleJobIdIsNotValidException(JobIdIsNotValidException exception) {
        return new ErrorMessageException(exception.getMessage());
    }

    @ExceptionHandler({JobRequestIllegalException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorMessageException handleJobRequestIllegalException(JobRequestIllegalException exception) {
        return new ErrorMessageException(exception.getMessage());
    }

    @ExceptionHandler({JobResponseNotFoundException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorMessageException handleJobResponseNotFoundException(JobResponseNotFoundException exception) {
        return new ErrorMessageException(exception.getMessage());
    }
}
