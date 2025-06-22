package com.bank.bankwithdrawalapi.controller;

import com.bank.bankwithdrawalapi.application.exceptions.AccountNotFoundException;
import com.bank.bankwithdrawalapi.application.exceptions.GeneralException;
import com.bank.bankwithdrawalapi.application.exceptions.InsufficientFundsException;
import com.bank.bankwithdrawalapi.application.exceptions.WithdrawalFailedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(WithdrawalFailedException.class)
    public ResponseEntity<String> handleWithdrawalFailedException(WithdrawalFailedException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
