package com.payment.orchestration.service.payment_orchestration_service.dto;

import java.time.LocalDateTime;

public class PaymentResponse {
    private String status;
    private String message;
    private String transactionReference;
    private String timestamp;

    public PaymentResponse(String status, String message) {
        this(status, message, null);
    }

    public PaymentResponse(String status, String message, String transactionReference) {
        this.status = status;
        this.message = message;
        this.transactionReference = transactionReference;
        this.timestamp = LocalDateTime.now().toString();
    }

    public PaymentResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
