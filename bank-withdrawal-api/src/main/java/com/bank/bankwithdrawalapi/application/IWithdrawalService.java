package com.bank.bankwithdrawalapi.application;

import java.math.BigDecimal;

public interface IWithdrawalService {

    public String withdraw(Long accountId, BigDecimal amount);
}
