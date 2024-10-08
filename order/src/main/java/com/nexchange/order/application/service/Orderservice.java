package com.nexchange.order.application.service;

import com.nexchange.order.domain.aggregate.OrderProposal;
import com.nexchange.order.infrastructure.persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Orderservice implements IOrderservice {

    @Autowired
    private OrderRepository orderRepository;

    // 创建提案
    @Override
    public OrderProposal createProposal(OrderProposal orderProposal) {
        // 提案初始化逻辑，可以增加更多逻辑，如验证
        return orderRepository.save(orderProposal);
    }

    @Override
    public void acceptProposal(String proposalId) {
        Optional<OrderProposal> proposalOptional = orderRepository.findById(proposalId);
        if (proposalOptional.isPresent()) {
            OrderProposal proposal = proposalOptional.get();
            proposal.acceptProposal();  // 更改状态为订单
            orderRepository.save(proposal);
        } else {
            throw new RuntimeException("Proposal not found with id: " + proposalId);
        }
    }
    
    @Override
    public void cancelProposal(String proposalId) {
        Optional<OrderProposal> proposalOptional = orderRepository.findById(proposalId);
        if (proposalOptional.isPresent()) {
            OrderProposal proposal = proposalOptional.get();
            proposal.cancelProposal();  // 更改状态为取消
            orderRepository.save(proposal);
        } else {
            throw new RuntimeException("Proposal not found with id: " + proposalId);
        }
    }
    
    // 查看客户的订单历史
    @Override
    public List<OrderProposal> getOrderHistory(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // 查看客户的待支付订单
    @Override
    public List<OrderProposal> getPendingOrders(String customerId) {
        // 这里可以加上订单状态为“待支付”的筛选条件
        return orderRepository.findByCustomerIdAndStatus(customerId, "PENDING");
    }
}
