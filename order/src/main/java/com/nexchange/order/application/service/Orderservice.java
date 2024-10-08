package com.nexchange.order.application.service;

import com.nexchange.order.domain.aggregate.OrderProposal;
import com.nexchange.order.domain.model.Order;
import com.nexchange.order.infrastructure.persistence.OrderRepository;
import com.nexchange.order.infrastructure.persistence.OrderProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nexchange.order.domain.model.OrderStatus;


import java.util.List;
import java.util.Optional;

@Service
public class Orderservice implements IOrderservice {

    @Autowired
    private OrderRepository orderRepository;  // 假设你有 OrderRepository 用于保存订单
    @Autowired
    private OrderProposalRepository orderProposalRepository;

    // 创建订单提案
    @Override
    public OrderProposal createProposal(OrderProposal orderProposal) {
        return orderProposalRepository.save(orderProposal);
    }

    // 接受提案，将其转化为订单
    @Override
    public void acceptProposal(String proposalId) {
        Optional<OrderProposal> proposalOptional = orderProposalRepository.findById(proposalId);
        if (proposalOptional.isPresent()) {
            OrderProposal proposal = proposalOptional.get();
            proposal.acceptProposal();  // 修改提案状态为 ORDER

            // 根据提案创建订单
            Order order = new Order(
                proposal.getProposalId(),
                proposal.getCustomerId(),
                proposal.getSellerId(),
                List.of(proposal.getProduct())  // 单一产品
            );
            order.setPaymentInfo(proposal.getPaymentInfo());
            orderRepository.save(order);  // 保存订单
            
            // 保存提案的变更
            orderProposalRepository.save(proposal);
        } else {
            throw new RuntimeException("Proposal not found with id: " + proposalId);
        }
    }

    // 取消提案
    @Override
    public void cancelProposal(String proposalId) {
        Optional<OrderProposal> proposalOptional = orderProposalRepository.findById(proposalId);
        if (proposalOptional.isPresent()) {
            OrderProposal proposal = proposalOptional.get();
            proposal.cancelProposal();  // 修改提案状态为 CANCELLED
            orderProposalRepository.save(proposal);  // 保存提案状态
        } else {
            throw new RuntimeException("Proposal not found with id: " + proposalId);
        }
    }

    // 获取历史订单
    @Override
    public List<OrderProposal> getOrderHistory(String customerId) {
        return orderProposalRepository.findByCustomerId(customerId);
    }

    // 获取待处理订单
    @Override
    public List<OrderProposal> getPendingOrders(String customerId) {
        return orderProposalRepository.findByCustomerIdAndStatus(customerId, OrderStatus.PENDING);
    }
}
