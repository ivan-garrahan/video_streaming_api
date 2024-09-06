package com.ivan.videostreamingapp;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// The PaymentController class handles HTTP requests related to payments
// It is annotated with @RestController, making it a RESTful web service controller
@RestController
@RequestMapping("/payments")
@Validated // Enables validation for request parameters and method arguments
public class PaymentController {

    // The paymentService is used to handle payment processing logic
    private final PaymentService paymentService;

    // The userService is used to validate if the provided credit card number is registered
    @Autowired
    private final UserService userService;

    // Constructor-based dependency injection for PaymentService and UserService
    // This ensures that the controller has access to the necessary services
    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    // Handler for GET requests
    @GetMapping
    public ResponseEntity<List<PaymentRequest>> getPayments() {
        List<PaymentRequest> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // The processPayment method handles POST requests to the /payments endpoint
    // It expects a JSON body containing the payment details, which are validated using @Valid
    @PostMapping
    public ResponseEntity<String> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        // Check if the credit card number in the request is registered using the UserService
        if (!userService.isCcnRegistered(paymentRequest.getCreditCardNumber())) {
            // If the credit card number is not registered, return a 404 Not Found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit Card Number not registered");
        }

        // If the credit card number is registered, process the payment using the PaymentService
        paymentService.processPayment(paymentRequest);

        // Return a 201 Created response indicating that the payment was processed successfully
        return ResponseEntity.status(HttpStatus.CREATED).body("PaymentRequest processed successfully");
    }
}