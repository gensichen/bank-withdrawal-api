package com.bank.bankwithdrawalapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GreetingResponseTest {

    @Test
    public void testGetMessage() {
        // Given
        String message = "Hello Test";
        GreetingResponse response = new GreetingResponse(message);

        // When
        String retrievedMessage = response.getMessage();

        // Then
        assertEquals(message, retrievedMessage, "getMessage should return the message passed to the constructor");
    }
}
