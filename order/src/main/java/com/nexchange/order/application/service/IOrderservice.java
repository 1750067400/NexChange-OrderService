package com.nexchange.order.application.service;

import com.nexchange.order.domain.aggregate.OrderProposal;

import java.util.List;

public interface IOrderservice {

    // 创建提案
    OrderProposal createProposal(OrderProposal orderProposal);

    // 接受提案
    void acceptProposal(String proposalId);

    // 取消提案
    void cancelProposal(String proposalId);

    // 查看客户的订单历史
    List<OrderProposal> getOrderHistory(String customerId);

    // 查看客户的待支付订单
    List<OrderProposal> getPendingOrders(String customerId);
}
