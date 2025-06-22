package com.bank.bankwithdrawalapi.application.exceptions;

public class GeneralException extends RuntimeException {
    public GeneralException() {
        super("A general error has occurred.");
    }

    public GeneralException(String message) {
        super(message);
    }
}
