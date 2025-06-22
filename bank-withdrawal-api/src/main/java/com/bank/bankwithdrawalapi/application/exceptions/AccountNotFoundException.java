package com.bank.bankwithdrawalapi.application.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException()
    {
        super("Account not found.");
    }
}
