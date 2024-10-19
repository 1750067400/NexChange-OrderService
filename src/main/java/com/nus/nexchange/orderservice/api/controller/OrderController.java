package com.nus.nexchange.orderservice.api.controller;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.nexchange.orderservice.infrastructure.messaging.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.application.command.OrderCommand;
import com.nus.nexchange.orderservice.application.query.OrderQuery;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderQuery orderQuery;

    @Autowired
    private OrderCommand orderCommand;

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderByOrderId(@PathVariable UUID orderId) {
        try {
            OrderDTO orderDTO = orderQuery.getOrderByOrderId(orderId);
            return ResponseEntity.ok(orderDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable("userId") UUID userId) {
        try {
            List<OrderDTO> orderDTOList = orderQuery.getOrdersByUserId(userId);
            return ResponseEntity.ok(orderDTOList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<?> getOrdersBySellerId(@PathVariable("userId") UUID userId) {
        try {
            List<OrderDTO> orderDTOList = orderQuery.getOrdersByUserId(userId);
            return ResponseEntity.ok(orderDTOList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/new-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            orderCommand.createOrder(orderDTO);
            String orderDTOJson = new ObjectMapper().writeValueAsString(orderDTO);
            kafkaProducer.sendMessage("CreateOrder",orderDTOJson);
            return ResponseEntity.ok("Order Created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> cancelOrder(@RequestParam UUID orderId) {
        try {
            OrderDTO orderDTO = orderCommand.cancelOrder(orderId);
            String orderDTOJson = new ObjectMapper().writeValueAsString(orderDTO);
            kafkaProducer.sendMessage("CancelOrder",orderDTOJson);
            return ResponseEntity.ok("Proposal canceled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}