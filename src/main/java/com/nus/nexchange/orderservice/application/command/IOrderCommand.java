package com.nus.nexchange.orderservice.application.command;

import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.api.dto.UUIDOrderDTO;

import java.util.UUID;

public interface IOrderCommand {
    void createOrder(OrderDTO orderDTO);

    UUIDOrderDTO expireOrder(UUID orderId);

    UUIDOrderDTO payOrder(UUID orderId);

//    UUIDOrderDTO shipOrder(UUID orderId);

    UUIDOrderDTO cancelOrder(UUID orderId);

//    UUIDOrderDTO completeOrder(UUID orderId);
}