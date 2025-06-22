package com.bank.bankwithdrawalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bank.bankwithdrawalapi.model.GreetingResponse;
import com.bank.bankwithdrawalapi.service.GreetingService;

@RestController
@RequestMapping("/api")
public class GreetingController {

    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/greeting/{name}")
    public GreetingResponse greetPerson(@PathVariable String name) {
        String greeting = greetingService.generateGreeting(name);
        return new GreetingResponse(greeting);
    }

    @PostMapping("/greeting")
    public GreetingResponse greetPersonPost(@RequestBody String name) {
        String greeting = greetingService.generateGreeting(name);
        return new GreetingResponse(greeting);
    }
}
