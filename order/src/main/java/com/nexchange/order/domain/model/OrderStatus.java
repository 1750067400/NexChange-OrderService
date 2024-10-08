package com.nexchange.order.domain.model;

public enum OrderStatus {
    PROPOSAL,   // 提案状态
    ORDER,      // 订单状态
    CANCELLED,  // 取消状态
    PENDING,    // 待处理状态
    ACCEPTED,   // 接受状态
    COMPLETED   // 完成状态
}
