package com.payment.orchestration.service.payment_orchestration_service.dto;

public class PaymentResponse {
    private String status;
    private String message;

    public PaymentResponse(String status, String message) {
        this.status = status;
        this.message = message;
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

}
