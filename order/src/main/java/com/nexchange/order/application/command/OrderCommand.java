package com.nexchange.order.application.command;

import com.nexchange.order.infrastructure.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCommand implements IOrderCommand {

    @Autowired
    private OrderRepository orderRepository;
}