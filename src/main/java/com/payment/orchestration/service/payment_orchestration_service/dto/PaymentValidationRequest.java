package com.payment.orchestration.service.payment_orchestration_service.dto;

import java.math.BigDecimal;

public class PaymentValidationRequest {

    private String transactionReference;
    private String customerId;
    private BigDecimal amount;

    public PaymentValidationRequest() {
    }

    public PaymentValidationRequest(String transactionReference, String customerId, BigDecimal amount) {
        this.transactionReference = transactionReference;
        this.customerId = customerId;
        this.amount = amount;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
