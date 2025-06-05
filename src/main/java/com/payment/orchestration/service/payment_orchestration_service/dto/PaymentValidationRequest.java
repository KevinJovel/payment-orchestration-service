package com.payment.orchestration.service.payment_orchestration_service.dto;

import java.math.BigDecimal;

public class PaymentValidationRequest {

    private String transactionReference;
    private String customerId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;

    public PaymentValidationRequest() {
    }

    public PaymentValidationRequest(String transactionReference, String customerId, BigDecimal amount,
            String paymentMethod, String status) {
        this.transactionReference = transactionReference;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
