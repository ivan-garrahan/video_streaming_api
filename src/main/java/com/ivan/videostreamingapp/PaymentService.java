package com.ivan.videostreamingapp;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean isCreditCardRegistered(String creditCardNumber) {
        return false;
    }

    public void processPayment(String creditCardNumber, String amount) {
    }
}
