package com.payment.orchestration.service.payment_orchestration_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentResponse;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition
@Tag(name = "internal") 
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<PaymentResponse> handleDuplicateTransaction(DuplicateTransactionException ex) {
        return ResponseEntity.badRequest().body(new PaymentResponse("error", ex.getMessage(), ex.getTransactionReference()));
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<PaymentResponse> handleInvalidPayment(InvalidPaymentException ex) {
        return ResponseEntity.badRequest().body(new PaymentResponse("error", ex.getMessage(), ex.getTransactionReference()));
    }

    @ExceptionHandler(ValidatorUnavailableException.class)
    public ResponseEntity<PaymentResponse> handleValidatorUnavailable(ValidatorUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                new PaymentResponse("error", ex.getMessage(), ex.getTransactionReference()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PaymentResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PaymentResponse("error", "Error interno: " + ex.getMessage()));
    }
}
