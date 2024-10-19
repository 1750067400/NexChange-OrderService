package com.nus.nexchange.orderservice.application.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nus.nexchange.orderservice.infrastructure.repository.OrderRepository;

@Service
public class OrderQuery implements IOrderQuery {

    @Autowired
    private OrderRepository orderRepository;
}