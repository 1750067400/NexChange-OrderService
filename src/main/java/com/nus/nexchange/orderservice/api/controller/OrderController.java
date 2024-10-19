package com.nus.nexchange.orderservice.api.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.application.command.OrderCommand;
import com.nus.nexchange.orderservice.application.query.OrderQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderQuery orderQuery;

    @Autowired
    private OrderCommand orderCommand;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable("userId") UUID userId) {
        return null;

    }

    @PostMapping("/order/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        return null;
    }

    @DeleteMapping("/order/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") UUID orderId) {

        return ResponseEntity.ok("Proposal canceled successfully");
    }

    @PostMapping("/order/accept/{orderId}")
    public ResponseEntity<?> acceptOrder(@PathVariable("orderId") UUID orderId) {

        return ResponseEntity.ok("Order accepted successfully");
    }


}