package com.bank.bankwithdrawalapi.application;

import com.bank.bankwithdrawalapi.application.exceptions.GeneralException;
import com.bank.bankwithdrawalapi.application.exceptions.InsufficientFundsException;
import com.bank.bankwithdrawalapi.application.exceptions.WithdrawalFailedException;
import com.bank.bankwithdrawalapi.domain.IBankAccountRepository;
import com.bank.bankwithdrawalapi.domain.IEventPublisher;
import com.bank.bankwithdrawalapi.domain.WithdrawalEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WithdrawalService implements IWithdrawalService {

    private final IBankAccountRepository _bankAccountRepository;
    private final IEventPublisher _eventPublisher;

    @Autowired
    public WithdrawalService(IBankAccountRepository bankAccountRepository, IEventPublisher eventPublisher)
    {
        _bankAccountRepository = bankAccountRepository;
        _eventPublisher = eventPublisher;
    }

    public String withdraw(Long accountId, BigDecimal amount) {
        try {
            VerifyWithdrawalIsAllowed(accountId, amount);
            WithdrawFunds(accountId, amount);
            PublishWithdrawalEvent(accountId, amount);

            return "Withdrawal successful";
        }
        catch (Exception ex) {
            // TODO: introduce a logger where the exception details (ex) can be logged.
            throw new GeneralException();
        }
    }

    private void VerifyWithdrawalIsAllowed(Long accountId, BigDecimal amount) {
        BigDecimal currentBalance = _bankAccountRepository.getBalance(accountId);
        if (currentBalance == null || currentBalance.compareTo(amount) <= 0) {
            throw new InsufficientFundsException();
        }
    }

    private void WithdrawFunds(Long accountId, BigDecimal amount) {
        int rowsAffected = _bankAccountRepository.updateBalance(accountId, amount);
        if (rowsAffected <= 0) {
            throw new WithdrawalFailedException();
        }
    }

    private void PublishWithdrawalEvent(Long accountId, BigDecimal amount) {
        WithdrawalEvent event = new WithdrawalEvent(amount, accountId, "SUCCESSFUL");
        _eventPublisher.publishEvent(event);
    }
}
