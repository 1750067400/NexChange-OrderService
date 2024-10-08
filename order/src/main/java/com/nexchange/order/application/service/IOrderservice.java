package com.nexchange.order.application.service;

import com.nexchange.order.domain.aggregate.OrderProposal;
import java.util.List;

public interface IOrderservice {

    // 创建订单提案
    OrderProposal createProposal(OrderProposal orderProposal);

    // 接受订单提案
    void acceptProposal(String proposalId);

    // 取消订单提案
    void cancelProposal(String proposalId);

    // 获取客户的历史订单
    List<OrderProposal> getOrderHistory(String customerId);

    // 获取客户待处理的订单
    List<OrderProposal> getPendingOrders(String customerId);
}
