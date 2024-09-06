package com.ivan.videostreamingapp;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<String> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        if (!paymentService.isCreditCardRegistered(paymentRequest.getCreditCardNumber())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit Card Number not registered");
        }
        paymentService.processPayment(paymentRequest.getCreditCardNumber(), paymentRequest.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).body("PaymentRequest processed successfully");
    }
}

