package com.bank.bankwithdrawalapi.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGreetPerson() throws Exception {
        // Given
        String name = "John";
        String expectedMessage = "Hello John";

        // When/Then
        mockMvc.perform(get("/api/greeting/{name}", name))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is(expectedMessage)));
    }

    @Test
    public void testGreetPersonWithSpecialChars() throws Exception {
        // Given
        String name = "John-Doe+Jr";
        String expectedMessage = "Hello John-Doe+Jr";

        // When/Then
        mockMvc.perform(get("/api/greeting/{name}", name))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is(expectedMessage)));
    }
}
