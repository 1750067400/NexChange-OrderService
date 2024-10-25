package com.nus.nexchange.orderservice.api.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.nus.nexchange.orderservice.domain.entity.OrderStatus;
import lombok.Data;


@Data
public class OrderDTO {
    private UUID orderId;

    private UUID refPostId;

    private String refPostTitle;

    private String refPostShortcutURL;

    private BigDecimal refPostPrice;

    private OrderStatus orderStatus;

    private SellerDetailDTO sellerDetail;

    private BuyerDetailDTO buyerDetail;

    private UUID userId;

    private LocalDateTime dateTimeCreated;
}
