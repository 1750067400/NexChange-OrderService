package com.nexchange.order.application.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexchange.order.infrastructure.repository.OrderRepository;

@Service
public class OrderQuery implements IOrderQuery {

    @Autowired
    private OrderRepository orderRepository;
}