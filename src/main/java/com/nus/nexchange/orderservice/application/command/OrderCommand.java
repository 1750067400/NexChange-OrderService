package com.nus.nexchange.orderservice.application.command;

import com.nus.nexchange.orderservice.infrastructure.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCommand implements IOrderCommand {

    @Autowired
    private OrderRepository orderRepository;
}