package com.bank.bankwithdrawalapi;

import com.bank.bankwithdrawalapi.application.WithdrawalService;
import com.bank.bankwithdrawalapi.controller.BankAccountController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
class BankWithdrawalApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WithdrawalService withdrawalService;

    @Test
    void givenWithdrawalRequest_whenBalanceIsPositive_shouldReturn200Ok() throws Exception {
        // Arrange
        Long accountId = 1L;
        BigDecimal withdrawalAmount = new BigDecimal("100.00");
        String expectedResponse = "Withdrawal successful";

        // Mock the withdrawal service response
        when(withdrawalService.withdraw(eq(accountId), eq(withdrawalAmount)))
                .thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/bank/withdraw")
                        .param("accountId", accountId.toString())
                        .param("amount", withdrawalAmount.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        // Verify that the service was called with correct parameters
        verify(withdrawalService).withdraw(accountId, withdrawalAmount);
    }
}
