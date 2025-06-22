package com.bank.bankwithdrawalapi.api;

import com.bank.bankwithdrawalapi.application.WithdrawalService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/bank")
public class BankAccountController {

    private final WithdrawalService _withdrawalService;

    @Autowired
    public BankAccountController(WithdrawalService withdrawalService) {
        _withdrawalService = withdrawalService;
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("accountId") Long accountId, @RequestParam("amount") BigDecimal amount) {
        return _withdrawalService.withdraw(accountId, amount);
    }
}

