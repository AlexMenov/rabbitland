package com.rabbits.orchestrator.orchestrator.exception;

final public class JobRequestIllegalException extends ErrorMessageException {
    public JobRequestIllegalException() {
        super("Illegal job request message!");
    }
}
