package com.bank.bankwithdrawalapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bank.bankwithdrawalapi.service.impl.GreetingServiceImpl;

@SpringBootTest
public class GreetingServiceTest {

    @Autowired
    private GreetingService greetingService;

    @Test
    public void testGenerateGreeting() {
        // Given
        String name = "John";
        String expectedGreeting = "Hello John";

        // When
        String actualGreeting = greetingService.generateGreeting(name);

        // Then
        assertEquals(expectedGreeting, actualGreeting, "The greeting message should match the expected format");
    }

    @Test
    public void testGenerateGreetingWithEmptyName() {
        // Given
        String name = "";
        String expectedGreeting = "Hello ";

        // When
        String actualGreeting = greetingService.generateGreeting(name);

        // Then
        assertEquals(expectedGreeting, actualGreeting, "The greeting should handle empty names correctly");
    }
}
