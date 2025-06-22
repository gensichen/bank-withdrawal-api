package com.bank.bankwithdrawalapi.application.exceptions;

public class WithdrawalFailedException extends RuntimeException {
    public WithdrawalFailedException() {
        super("Withdrawal failed");
    }
}
