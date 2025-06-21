package com.bank.bankwithdrawalapi.service.impl;

import org.springframework.stereotype.Service;
import com.bank.bankwithdrawalapi.service.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String generateGreeting(String name) {
        return "Hello " + name;
    }
}
