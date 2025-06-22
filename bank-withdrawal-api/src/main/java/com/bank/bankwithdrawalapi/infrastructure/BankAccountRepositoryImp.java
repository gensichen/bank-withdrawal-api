package com.bank.bankwithdrawalapi.infrastructure;

import com.bank.bankwithdrawalapi.domain.IBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
class BankAccountRepositoryImp implements IBankAccountRepository {

    private final JdbcTemplate _jdbcTemplate;

    private static final String UPDATE_BALANCE_SQL =  "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?";
    private static final String GET_BALANCE_SQL = "SELECT balance FROM accounts WHERE id = ?";

    @Autowired
    public BankAccountRepositoryImp(JdbcTemplate jdbcTemplate) {
        _jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int updateBalance(Long accountId, BigDecimal amount) {
        return _jdbcTemplate.update(UPDATE_BALANCE_SQL, amount, accountId);
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        try {
            return _jdbcTemplate.queryForObject(GET_BALANCE_SQL, BigDecimal.class, accountId);
        }
        catch (EmptyResultDataAccessException e) {
            return null; // Account not found
        }
    }
}
