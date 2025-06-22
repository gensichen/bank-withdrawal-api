package com.bank.bankwithdrawalapi.application;

import java.math.BigDecimal;

public interface IWithdrawalService {

    String withdraw(Long accountId, BigDecimal amount);
}
