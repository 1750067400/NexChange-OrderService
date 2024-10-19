package com.nus.nexchange.orderservice.application.query;

import com.nus.nexchange.orderservice.api.dto.OrderDTO;

import java.util.List;
import java.util.UUID;

public interface IOrderQuery {
    public OrderDTO getOrderByOrderId(UUID orderId);
    public List<OrderDTO> getOrdersByUserId(UUID userId);
    public List<OrderDTO> getOrdersBySellerId(UUID sellerId);
}