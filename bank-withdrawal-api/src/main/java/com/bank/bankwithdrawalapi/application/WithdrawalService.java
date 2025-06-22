package com.bank.bankwithdrawalapi.application;

import com.bank.bankwithdrawalapi.application.exceptions.AccountNotFoundException;
import com.bank.bankwithdrawalapi.application.exceptions.GeneralException;
import com.bank.bankwithdrawalapi.application.exceptions.InsufficientFundsException;
import com.bank.bankwithdrawalapi.application.exceptions.WithdrawalFailedException;
import com.bank.bankwithdrawalapi.domain.IBankAccountRepository;
import com.bank.bankwithdrawalapi.domain.IEventPublisher;
import com.bank.bankwithdrawalapi.domain.WithdrawalEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WithdrawalService implements IWithdrawalService {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawalService.class);
    private final IBankAccountRepository _bankAccountRepository;
    private final IEventPublisher _eventPublisher;

    @Autowired
    public WithdrawalService(IBankAccountRepository bankAccountRepository, IEventPublisher eventPublisher)
    {
        _bankAccountRepository = bankAccountRepository;
        _eventPublisher = eventPublisher;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String withdraw(Long accountId, BigDecimal amount) {
        try {
            verifyWithdrawalIsAllowed(accountId, amount);
            withdrawFunds(accountId, amount);
            publishWithdrawalEvent(accountId, amount);

            return "Withdrawal successful";
        }
        catch (InsufficientFundsException | WithdrawalFailedException e) {
            // Rethrow known exceptions
            logger.warn("Withdrawal operation failed: {}", e.getMessage());
            throw e;
        }
        catch (Exception ex) {
            logger.error("Unexpected error during withdrawal operation", ex);
            throw new GeneralException();
        }
    }

    private void verifyWithdrawalIsAllowed(Long accountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        if (accountId == null || accountId < 0) {
            throw new IllegalArgumentException("Account ID must be positive");
        }

        BigDecimal currentBalance = _bankAccountRepository.getBalance(accountId);
        if (currentBalance == null) {
            logger.warn("Account not found: {}", accountId);
            throw new AccountNotFoundException();
        }

        if (currentBalance.compareTo(amount) < 0) {
            logger.info("Insufficient funds for account {}: requested {} but balance is {}", accountId, amount, currentBalance);
            throw new InsufficientFundsException();
        }
    }

    private void withdrawFunds(Long accountId, BigDecimal amount) {
        int rowsAffected = _bankAccountRepository.updateBalance(accountId, amount);
        if (rowsAffected <= 0) {
            logger.warn("Failed to update balance for account {}", accountId);
            throw new WithdrawalFailedException();
        }
    }

    private void publishWithdrawalEvent(Long accountId, BigDecimal amount) {
        WithdrawalEvent event = new WithdrawalEvent(amount, accountId, "SUCCESSFUL");
        _eventPublisher.publishEvent(event);
        logger.debug("Published withdrawal event for account {}: {}", accountId, amount);
    }
}
