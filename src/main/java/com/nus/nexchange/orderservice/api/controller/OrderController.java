package com.nus.nexchange.orderservice.api.controller;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.nexchange.orderservice.api.dto.UUIDOrderDTO;
import com.nus.nexchange.orderservice.infrastructure.messaging.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.application.command.OrderCommand;
import com.nus.nexchange.orderservice.application.query.OrderQuery;


@RestController
@RequestMapping("/api/order-system/orders")
public class OrderController {

    private final OrderQuery orderQuery;

    private final OrderCommand orderCommand;

    private final KafkaProducer kafkaProducer;

    @Autowired
    public OrderController(OrderQuery orderQuery, OrderCommand orderCommand, KafkaProducer kafkaProducer) {
        this.orderQuery = orderQuery;
        this.orderCommand = orderCommand;
        this.kafkaProducer = kafkaProducer;
    }

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
            List<OrderDTO> orderDTOList = orderQuery.getOrdersBySellerId(userId);
            return ResponseEntity.ok(orderDTOList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/new-order")
//    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
//        try {
//            orderCommand.createOrder(orderDTO);
//            String orderDTOJson = new ObjectMapper().writeValueAsString(orderDTO);
////            kafkaProducer.sendMessage("CreateOrder",orderDTOJson);
//            return ResponseEntity.ok("Order Created");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping("/new-order")
    public ResponseEntity<String> createOrderById(@RequestBody UUIDOrderDTO UUIDOrderDTO) {
        try {
            UUIDOrderDTO createOrderDTO = new UUIDOrderDTO(UUIDOrderDTO.getUserId(), UUIDOrderDTO.getPostId());
            String createOrderDTOJson = new ObjectMapper().writeValueAsString(createOrderDTO);
            kafkaProducer.sendMessage("CreateOrder", createOrderDTOJson);
            return ResponseEntity.ok("Order Creating");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam UUID orderId) {
        try {
            UUIDOrderDTO UUIDOrderDTO = orderCommand.cancelOrder(orderId);
            String orderDTO = new ObjectMapper().writeValueAsString(UUIDOrderDTO);

            kafkaProducer.sendMessage("CancelOrder", orderDTO);
            return ResponseEntity.ok("Order canceled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/expire")
    public ResponseEntity<?> expireOrder(@RequestParam UUID orderId) {
        try {
            UUIDOrderDTO UUIDOrderDTO = orderCommand.expireOrder(orderId);
            String orderDTO = new ObjectMapper().writeValueAsString(UUIDOrderDTO);

            kafkaProducer.sendMessage("ExpireOrder", orderDTO);
            return ResponseEntity.ok("Order expire successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pay")
    public ResponseEntity<?> payOrder(@RequestParam UUID orderId) {
        try {
            UUIDOrderDTO UUIDOrderDTO = orderCommand.payOrder(orderId);
            String orderDTO = new ObjectMapper().writeValueAsString(UUIDOrderDTO);

            kafkaProducer.sendMessage("PayOrder", orderDTO);
            return ResponseEntity.ok("Order pay successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}