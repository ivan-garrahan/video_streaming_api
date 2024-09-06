package com.ivan.videostreamingapp;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PaymentRequest {

    private Long id;

    @NotNull(message = "Credit Card Number is required")
    @Pattern(regexp = "\\d{16}", message = "Credit Card Number must be 16 digits")
    private String creditCardNumber;

    @NotNull(message = "Amount is required")
    @Pattern(regexp = "\\d{1,3}", message = "Amount must be between 1 and 3 digits")
    private String amount;

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
