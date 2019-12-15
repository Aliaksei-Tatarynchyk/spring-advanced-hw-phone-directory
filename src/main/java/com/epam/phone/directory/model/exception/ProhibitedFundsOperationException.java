package com.epam.phone.directory.model.exception;

public class ProhibitedFundsOperationException extends GenericFundsException {
    public ProhibitedFundsOperationException() {
    }

    public ProhibitedFundsOperationException(String message) {
        super(message);
    }
}
