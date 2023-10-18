package com.rabbits.orchestrator.orchestrator.exception;

public class JobRequestIllegalException extends ErrorMessageException {
    public JobRequestIllegalException() {
        super("Illegal job request message!");
    }
}
