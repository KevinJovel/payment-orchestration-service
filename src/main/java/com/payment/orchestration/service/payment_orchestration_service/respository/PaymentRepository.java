package com.payment.orchestration.service.payment_orchestration_service.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.orchestration.service.payment_orchestration_service.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByTransactionReference(String transactionReference);
}