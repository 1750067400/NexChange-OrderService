package com.nus.nexchange.orderservice.application.command;

import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.api.dto.UUIDOrderDTO;

import java.util.UUID;

public interface IOrderCommand {
    public void createOrder(OrderDTO orderDTO);
    public UUIDOrderDTO expireOrder(UUID orderId);
    public void payOrder(UUID orderId);
    public void shipOrder(UUID orderId);
    public UUIDOrderDTO cancelOrder(UUID orderId);
    public void completeOrder(UUID orderId);
}