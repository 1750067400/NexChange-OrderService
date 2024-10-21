package com.nus.nexchange.orderservice.infrastructure.messaging.dto;

import com.nus.nexchange.orderservice.api.dto.BuyerDetailDTO;
import com.nus.nexchange.orderservice.api.dto.SellerDetailDTO;
import com.nus.nexchange.orderservice.domain.entity.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UserOrderDTO {
    private UUID orderId;

    private String refPostTitle;

    private String refPostShortcutURL;

    private BigDecimal refPostPrice;

    private OrderStatus orderStatus;

    private UUID userId;
}
