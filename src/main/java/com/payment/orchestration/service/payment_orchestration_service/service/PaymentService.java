package com.payment.orchestration.service.payment_orchestration_service.service;

import java.time.LocalDateTime;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentRequest;
import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentResponse;
import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentValidationRequest;
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
            payment.setStatus("APPROVED");
            paymentRepository.save(payment);
            return new PaymentResponse("success", "Pago procesado con éxito");
        } else {
            payment.setStatus("REJECTED");
            paymentRepository.save(payment);
            return new PaymentResponse("success", "Pago procesado con éxito");
        }
    }

    private boolean callValidator(Payment payment) {
        RestTemplate restTemplate = new RestTemplate();

        String validatorUrl = "http://localhost:8081/payments/apply"; // URL del segundo servicio

        // Crear request con lo necesario para validar y aplicar
        PaymentValidationRequest request = new PaymentValidationRequest(
            payment.getTransactionReference(),
            payment.getCustomer().getCustomerId(),
            payment.getAmount());

        try {
            ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(
                    validatorUrl,
                    request,
                    PaymentResponse.class);

            return response.getStatusCode() == HttpStatus.OK &&
                    response.getBody() != null &&
                    "SUCCESS".equals(response.getBody().getStatus());

        } catch (Exception ex) {
            System.out.println("Error al llamar al validador: " + ex.getMessage());
            return false;
        }
    }

    // private String generateTransactionReference() {
    // // Genera un string único para la referencia, por ejemplo UUID o custom
    // return java.util.UUID.randomUUID().toString();
    // }

}
