package com.rabbits.orchestrator.orchestrator.exception;

public class JobIdIsNotValidException extends ErrorMessageException {
    public JobIdIsNotValidException () {
        super("Job id is not valid!");
    }
}
