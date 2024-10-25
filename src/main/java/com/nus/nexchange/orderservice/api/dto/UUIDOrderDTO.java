package com.nus.nexchange.orderservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UUIDOrderDTO {
    private UUID orderId;
    private UUID postId;
    private UUID userId;
    private UUID secret;

    public UUIDOrderDTO(UUID orderId, UUID userId, UUID postId) {
        this.secret = UUID.randomUUID();
        this.orderId = orderId;
        this.userId = userId;
        this.postId = postId;
    }
}
