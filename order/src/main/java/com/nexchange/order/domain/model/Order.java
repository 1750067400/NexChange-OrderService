package com.nexchange.order.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private UUID orderId;
    private UUID buyerId;
    private UUID sellerId;
    private String postTitle;
    private String postPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;

    public Order(UUID buyerId, UUID sellerId, String postTitle, String postPrice) {
        this.orderId = UUID.randomUUID();
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.postTitle = postTitle;
        this.postPrice = postPrice;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = OrderStatus.PROPOSAL;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostPrice() {
        return postPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}

enum OrderStatus {
    PROPOSAL,
    ACCEPTED,
    UNPAID,
    PAID,
    SHIPPED,
    SHIPPING,
    RECEIVED,
    COMPLETED,
    CANCELED
}