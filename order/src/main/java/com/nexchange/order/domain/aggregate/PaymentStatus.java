package com.nexchange.order.domain.aggregate;

public enum PaymentStatus {
    PENDING,    // 待支付
    PAID,       // 已支付
    FAILED,     // 支付失败
    REFUNDED    // 已退款
}
