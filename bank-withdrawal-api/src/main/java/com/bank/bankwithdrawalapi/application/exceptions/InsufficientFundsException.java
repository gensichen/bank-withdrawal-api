package com.bank.bankwithdrawalapi.application.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds for withdrawal");
    }
}
