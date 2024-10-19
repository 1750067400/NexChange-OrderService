package com.nus.nexchange.orderservice.domain.entity;

public enum OrderStatus {
    UNPAID,
    EXPIRED,
    PAID,
    SHIPPED,
    SHIPPING,
    RECEIVED,
    CANCELED,
    COMPLETED
}