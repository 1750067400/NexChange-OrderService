package com.nexchange.order.domain.aggregate;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private UUID orderId;
    private UUID postId;
    private UUID sellerId;
    private UUID buyerId;
    private String postTitle;
    private String postPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;

    public Order(UUID postId, UUID sellerId, UUID buyerId, String postTitle, String postPrice) {
        this.orderId = UUID.randomUUID();
        this.postId = postId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.postTitle = postTitle;
        this.postPrice = postPrice;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = OrderStatus.PROPOSAL;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getPostId() {
        return postId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public UUID getBuyerId() {
        return buyerId;
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

    public enum OrderStatus {
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
}