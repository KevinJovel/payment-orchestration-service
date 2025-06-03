package com.payment.orchestration.service.payment_orchestration_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.payment.orchestration.service.payment_orchestration_service.dto.PaymentResponse;

public class GlobalExceptionHandler {
   @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<PaymentResponse> handleDuplicateTransaction(DuplicateTransactionException ex) {
        return ResponseEntity.badRequest().body(new PaymentResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<PaymentResponse> handleInvalidPayment(InvalidPaymentException ex) {
        return ResponseEntity.badRequest().body(new PaymentResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PaymentResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PaymentResponse("error", "Error interno: " + ex.getMessage()));
    }
}
