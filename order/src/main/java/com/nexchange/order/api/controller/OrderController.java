package com.nexchange.order.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @GetMapping("/test")
    public String testEndpoint() {
        return "Order service is up and running!";
    }

    @PostMapping("/proposal/create")
    public ResponseEntity<String> createProposal() {
        // Placeholder for proposal creation logic
        return ResponseEntity.status(HttpStatus.CREATED).body("Proposal has been created successfully!");
    }

    @PostMapping("/proposal/accept/{proposalId}")
    public ResponseEntity<String> acceptProposal(@PathVariable String proposalId) {
        // Placeholder for accepting proposal and converting to order logic
        return ResponseEntity.ok("Proposal " + proposalId + " has been accepted and converted to an order.");
    }

    @DeleteMapping("/proposal/cancel/{proposalId}")
    public ResponseEntity<String> cancelProposal(@PathVariable String proposalId) {
        // Placeholder for canceling proposal logic
        return ResponseEntity.ok("Proposal " + proposalId + " has been canceled.");
    }

    @GetMapping("/customer/history/{customerId}")
    public ResponseEntity<List<String>> getCustomerOrderHistory(@PathVariable String customerId) {
        // Placeholder for fetching customer order history logic
        return ResponseEntity.ok(List.of("Order1", "Order2", "Order3"));
    }

    @GetMapping("/payment/status/{orderId}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String orderId) {
        // Placeholder for fetching payment status logic
        return ResponseEntity.ok("Payment status for order " + orderId + " is: PAID");
    }
}