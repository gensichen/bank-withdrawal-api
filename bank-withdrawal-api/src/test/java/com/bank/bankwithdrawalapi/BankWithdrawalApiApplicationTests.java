package com.bank.bankwithdrawalapi;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.bankwithdrawalapi.controller.BankAccountController;
import org.junit.jupiter.api.BeforeEach;
import software.amazon.awssdk.services.sns.SnsClient;

@SpringBootTest
@AutoConfigureMockMvc
class BankWithdrawalApiApplicationTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private SnsClient snsClient;

    @InjectMocks
    private BankAccountController bankAccountController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void givenValidWithdrawal_whenBalanceIsPositive_shouldReturn200Ok() throws Exception {
        // Arrange
        Long accountId = 1L;
        BigDecimal withdrawalAmount = new BigDecimal("100.00");
        BigDecimal currentBalance = new BigDecimal("500.00");

        // Mock the database query to return a sufficient balance
        Mockito.when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(BigDecimal.class)))
               .thenReturn(currentBalance);

        // Mock the database update to return 1 (1 row affected)
        Mockito.when(jdbcTemplate.update(anyString(), any(BigDecimal.class), eq(accountId)))
               .thenReturn(1);

        // Act & Assert
        mockMvc.perform(post("/bank/withdraw")
                .param("accountId", accountId.toString())
                .param("amount", withdrawalAmount.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Withdrawal successful"));

        // Verify that the database was queried with the correct account ID
        Mockito.verify(jdbcTemplate).queryForObject(anyString(), eq(new Object[]{accountId}), eq(BigDecimal.class));

        // Verify that the update was called with the correct parameters
        Mockito.verify(jdbcTemplate).update(anyString(), eq(withdrawalAmount), eq(accountId));
    }

}
