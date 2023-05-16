package com.diploma.zoo_hotel.service;

import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestService {
    private final UserService userService;
    private final CustomerService customerService;

    @EventListener
    public void seed(ContextRefreshedEvent event) {

    }
}
