package com.payment.orchestration.service.payment_orchestration_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentRequest;
import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentResponse;
import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentValidationRequest;
import com.payment.orchestration.service.payment_orchestration_service.entity.Customer;
import com.payment.orchestration.service.payment_orchestration_service.entity.Payment;
import com.payment.orchestration.service.payment_orchestration_service.exception.DuplicateTransactionException;
import com.payment.orchestration.service.payment_orchestration_service.exception.InvalidPaymentException;
import com.payment.orchestration.service.payment_orchestration_service.respository.PaymentRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Value("${payment.max-amount}")
    private BigDecimal maxAmount;
    @Value("${payment.validator.url}")
    private String validatorUrl;

    private final PaymentRepository paymentRepository;
    private final CustomerService customerService;

    public PaymentService(PaymentRepository paymentRepository, CustomerService customerService) {
        this.paymentRepository = paymentRepository;
        this.customerService = customerService;
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        // Validar que el cliente existe
        Optional<Customer> customerOpt = customerService.getCustomerByCustomerId(request.getCustomerId());
        if (customerOpt.isEmpty()) {
            return new PaymentResponse(
                    "error",
                    "Cliente no encontrado",
                    request.getTransactionReference());
        }
        validatePaymentAmount(request.getPaymentAmount(), request.getTransactionReference());
        validateDuplicateTransaction(request.getTransactionReference());

        // Crear entidad Payment con los datos del request
        Payment payment = new Payment();
        payment.setCustomer(customerOpt.get());
        payment.setAmount(request.getPaymentAmount());
        payment.setDescription(request.getDescription());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setStatus("PENDING");
        payment.setPaymentDate(LocalDateTime.now());

        // Guardar pago
        paymentRepository.save(payment);
        // Llamada al servicio validador
        boolean success = callValidator(payment);
        if (success) {
            payment.setStatus("PAID");
            paymentRepository.save(payment);
            return new PaymentResponse("success", "Pago procesado con éxito",request.getTransactionReference());
        } else {
            payment.setStatus("REJECTED");
            paymentRepository.save(payment);
            return new PaymentResponse("error", "Ocurrió un error al registrar el pago.",request.getTransactionReference());
        }
    }

    private boolean callValidator(Payment payment) {

        RestTemplate restTemplate = new RestTemplate();

        // Crear request con lo necesario para validar y aplicar
        PaymentValidationRequest request = new PaymentValidationRequest(
                payment.getTransactionReference(),
                payment.getCustomer().getCustomerId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus());

        try {
            ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(
                    validatorUrl,
                    request,
                    PaymentResponse.class);

            return response.getStatusCode() == HttpStatus.OK &&
                    Optional.ofNullable(response.getBody())
                            .map(body -> "SUCCESS".equalsIgnoreCase(body.getStatus()))
                            .orElse(false);

        } catch (Exception ex) {
            System.out.println("Error al llamar al validador: " + ex.getMessage());
            return false;
        }
    }

    private void validatePaymentAmount(BigDecimal amount, String transactionReference) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPaymentException("El monto del pago debe ser mayor a cero.", transactionReference);
        }
        if (amount.compareTo(maxAmount) > 0) {
            throw new InvalidPaymentException("El monto excede el máximo permitido.", transactionReference);
        }
    }

    private void validateDuplicateTransaction(String transactionReference) {
        if (paymentRepository.existsByTransactionReference(transactionReference)) {
            throw new DuplicateTransactionException("La transacción ya fue registrada.", transactionReference);
        }
    }
}
