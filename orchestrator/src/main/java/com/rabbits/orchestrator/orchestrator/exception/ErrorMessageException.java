package com.rabbits.orchestrator.orchestrator.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ErrorMessageException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final Date timestamp;
    private final String message;
    public ErrorMessageException (String message, HttpStatus httpStatus) {
        super(message);
        this.timestamp = new Date();
        this.httpStatus = httpStatus;
        this.message = message;
    }
    public ErrorMessageException (String message) {
        super(message);
        this.timestamp = new Date();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }
}
