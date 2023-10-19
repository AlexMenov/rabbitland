package com.rabbits.orchestrator.orchestrator.exception;

import org.springframework.http.HttpStatus;

public class JobResponseNotFoundException extends ErrorMessageException {
    public JobResponseNotFoundException() {
        super("Job(s) not found or job creating(updating) error!", HttpStatus.NOT_FOUND);
    }
}
