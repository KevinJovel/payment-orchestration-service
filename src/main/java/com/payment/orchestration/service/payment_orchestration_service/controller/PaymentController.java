package com.payment.orchestration.service.payment_orchestration_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @PostMapping
    public String processPayment() {
        //TODO: process POST request
        
        return null;
    }
    
}
