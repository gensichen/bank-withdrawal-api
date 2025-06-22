package com.bank.bankwithdrawalapi.application;

import com.bank.bankwithdrawalapi.application.exceptions.InsufficientFundsException;
import com.bank.bankwithdrawalapi.domain.IBankAccountRepository;
import com.bank.bankwithdrawalapi.domain.IEventPublisher;
import com.bank.bankwithdrawalapi.domain.WithdrawalEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

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

        BigDecimal currentBalance = _bankAccountRepository.getBalance(accountId);

        if (currentBalance != null && currentBalance.compareTo(amount) >= 0) {
            // Update balance
            //sql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            int rowsAffected = _bankAccountRepository.updateBalance(accountId, amount);
            if (rowsAffected > 0) {
                // After a successful withdrawal, publish a withdrawal event to SNS
                WithdrawalEvent event = new WithdrawalEvent(amount, accountId, "SUCCESSFUL");
                _eventPublisher.publishEvent(event);

                return "Withdrawal successful";
            } else {
                // In case the update fails for reasons other than a balance check
                return "Withdrawal failed";
            }
        } else {
            // Insufficient funds
            throw new InsufficientFundsException();
        }
    }
}
