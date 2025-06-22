package com.bank.bankwithdrawalapi.infrastructure;

import com.bank.bankwithdrawalapi.domain.IBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BankAccountRespositoryImp implements IBankAccountRepository {

    private final JdbcTemplate _jdbcTemplate;

    @Autowired
    public BankAccountRespositoryImp(JdbcTemplate jdbcTemplate) {
        _jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int updateBalance(Long accountId, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        return _jdbcTemplate.update(sql, amount, accountId);
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        String sql = "SELECT balance FROM accounts WHERE id = ?";
        return _jdbcTemplate.queryForObject(sql, new Object[]{accountId}, BigDecimal.class);
    }
}
