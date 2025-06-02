package com.payment.orchestration.service.payment_orchestration_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentRequest;
import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentResponse;
import com.payment.orchestration.service.payment_orchestration_service.service.PaymentService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.payment.orchestration.service.payment_orchestration_service.entity.Payment;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> list() {
        return paymentService.findAll();
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }

}
