package com.payment.orchestration.service.payment_orchestration_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentRequest;
import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentResponse;
import com.payment.orchestration.service.payment_orchestration_service.entity.Customer;
import com.payment.orchestration.service.payment_orchestration_service.entity.Payment;
import com.payment.orchestration.service.payment_orchestration_service.respository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CustomerService customerService;

    public PaymentService(PaymentRepository paymentRepository, CustomerService customerService) {
        this.paymentRepository = paymentRepository;
        this.customerService = customerService;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        // Validar que el cliente existe
        Optional<Customer> customerOpt = customerService.getCustomerByCustomerId(request.getCustomerId());
        if (customerOpt.isEmpty()) {
            return new PaymentResponse("error", "Cliente no encontrado");
        }

        // Crear entidad Payment con los datos del request
        Payment payment = new Payment();
        payment.setCustomer(customerOpt.get());
        payment.setAmount(request.getPaymentAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(generateTransactionReference());
        payment.setPaymentDate(LocalDateTime.now());

        // Guardar pago
        paymentRepository.save(payment);

        // Retornar respuesta exitosa
        return new PaymentResponse("success", "Pago procesado con éxito");
    }

    private String generateTransactionReference() {
        // Genera un string único para la referencia, por ejemplo UUID o custom
        return java.util.UUID.randomUUID().toString();
    }

    // @Transactional(readOnly = true)
    // public List<Payment> findAll() {
    //     return paymentRepository.findAll();
    // }
}
