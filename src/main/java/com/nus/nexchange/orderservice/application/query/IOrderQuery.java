package com.nus.nexchange.orderservice.application.query;

import com.nus.nexchange.orderservice.api.dto.OrderDTO;

import java.util.List;
import java.util.UUID;

public interface IOrderQuery {
    OrderDTO getOrderByOrderId(UUID orderId);

    List<OrderDTO> getOrdersByUserId(UUID userId);

    List<OrderDTO> getOrdersBySellerId(UUID sellerId);
}