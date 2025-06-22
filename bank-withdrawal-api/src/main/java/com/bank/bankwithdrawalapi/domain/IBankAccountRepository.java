package com.bank.bankwithdrawalapi.domain;

import java.math.BigDecimal;

/**
 * Defines the operations to manage bank account balances.
 * This interface provides methods for updating and retrieving the
 * balance of bank accounts identified by unique account IDs.
 */
public interface IBankAccountRepository {
    /**
     * Updates the balance of a bank account identified by the given account ID.
     *
     * @param accountId the unique identifier of the bank account whose balance is to be updated.
     * @param amount    the amount to update the balance by, this can be positive or negative.
     * @return int returns number of rows effected.
     */
    int updateBalance(Long accountId, BigDecimal amount);

    /**
     * Retrieves the current balance of a bank account identified by the given account ID.
     *
     * @param accountId the unique identifier of the bank account whose balance is to be retrieved
     * @return the current balance of the specified bank account
     */
    BigDecimal getBalance(Long accountId);
}