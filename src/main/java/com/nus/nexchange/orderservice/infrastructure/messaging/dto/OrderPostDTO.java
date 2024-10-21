package com.nus.nexchange.orderservice.infrastructure.messaging.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderPostDTO {
    private UUID postId;

    private UUID userId;

    private String postTittle;

    private String postName;

    private String postDescription;

    private BigDecimal postPrice;

    private String postShortcutURL;

    private PostSellerDTO postSeller;

    private UUID orderId;
}
