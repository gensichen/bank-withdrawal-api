package com.bank.bankwithdrawalapi.controller;

import com.bank.bankwithdrawalapi.application.IWithdrawalService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/bank")
public class BankAccountController {

    private final IWithdrawalService _withdrawalService;

    @Autowired
    public BankAccountController(IWithdrawalService withdrawalService) {
        _withdrawalService = withdrawalService;
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("accountId") Long accountId, @RequestParam("amount") BigDecimal amount) {
        return _withdrawalService.withdraw(accountId, amount);
    }
}

