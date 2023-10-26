package com.rabbits.orchestrator.orchestrator.exception;

final public class JobResponseNotFoundException extends ErrorMessageException {
    public JobResponseNotFoundException() {
        super("Job(s) not found or job creating(updating) error!");
    }
}
