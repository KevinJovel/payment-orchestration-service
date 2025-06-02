package com.payment.orchestration.service.payment_orchestration_service.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.orchestration.service.payment_orchestration_service.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);

    List<Payment> findAll();
}
