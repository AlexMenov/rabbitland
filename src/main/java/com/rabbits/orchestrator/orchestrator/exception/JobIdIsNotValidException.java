package com.rabbits.orchestrator.orchestrator.exception;

final public class JobIdIsNotValidException extends ErrorMessageException {
    public JobIdIsNotValidException () {
        super("Job id is not valid!");
    }
}
