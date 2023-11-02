package com.rabbits.orchestrator.orchestrator.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final Date timestamp;
    private final String message;

    public ErrorResponse (String message, HttpStatus httpStatus) {
        this.timestamp = new Date();
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
