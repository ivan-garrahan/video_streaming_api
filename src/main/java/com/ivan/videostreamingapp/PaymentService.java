package com.ivan.videostreamingapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PaymentService {

    // In-memory list to store PaymentRequest objects
    private final List<PaymentRequest> payments = new ArrayList<>();

    // Atomic counter to generate unique IDs for each payment
    private final AtomicLong counter = new AtomicLong(1);

    // Method to get all payment requests
    public List<PaymentRequest> getAllPayments() {
        return payments;
    }

    // Method to get a payment request by its ID
    public Optional<PaymentRequest> getPaymentById(Long id) {
        return payments.stream().filter(payment -> payment.getId().equals(id)).findFirst();
    }

    // Method to create a new payment request
    public PaymentRequest processPayment(PaymentRequest paymentRequest) {
        paymentRequest.setId(counter.getAndIncrement());  // Assign a unique ID
        payments.add(paymentRequest);  // Add the payment request to the list
        return paymentRequest;
    }

    // Method to update an existing payment request by its ID
    public Optional<PaymentRequest> updatePayment(Long id, PaymentRequest updatedPaymentRequest) {
        return getPaymentById(id).map(existingPayment -> {
            existingPayment.setCreditCardNumber(updatedPaymentRequest.getCreditCardNumber());
            existingPayment.setAmount(updatedPaymentRequest.getAmount());
            return existingPayment;
        });
    }

    // Method to delete a payment request by its ID
    public boolean deletePayment(Long id) {
        return payments.removeIf(payment -> payment.getId().equals(id));
    }

    // Method to check if a credit card number is registered in any payment request
    public boolean isCcnRegistered(String creditCardNumber) {
        return payments.stream()
                .anyMatch(payment -> payment.getCreditCardNumber().equals(creditCardNumber));
    }
}
