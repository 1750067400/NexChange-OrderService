package com.nexchange.order.api.controller;

import com.nexchange.order.application.command.OrderCommand;
import com.nexchange.order.application.query.OrderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
}