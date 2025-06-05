package com.payment.orchestration.service.payment_orchestration_service.exception;

public class DuplicateTransactionException extends RuntimeException {
    private final String transactionReference;

    public DuplicateTransactionException(String message, String transactionReference) {
        super(message);
        this.transactionReference = transactionReference;
    }

    public String getTransactionReference() {
        return transactionReference;
    }
}
