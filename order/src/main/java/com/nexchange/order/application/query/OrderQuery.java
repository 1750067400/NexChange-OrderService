package com.nexchange.order.application.query;

import com.nexchange.order.infrastructure.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderQuery implements IOrderQuery {

    @Autowired
    private OrderRepository orderRepository;
}