package com.epam.phone.directory.model.exception;

public class InsufficientFundsException extends GenericFundsException {
    public InsufficientFundsException() {
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
