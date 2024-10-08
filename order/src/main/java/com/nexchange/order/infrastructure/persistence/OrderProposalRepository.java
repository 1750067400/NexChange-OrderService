package com.nexchange.order.infrastructure.persistence;

import com.nexchange.order.domain.aggregate.OrderProposal;
import com.nexchange.order.domain.model.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProposalRepository extends JpaRepository<OrderProposal, String> {
    
    // 根据客户 ID 查找所有提案
    List<OrderProposal> findByCustomerId(String customerId);

    // 根据客户 ID 和提案状态查找提案
    List<OrderProposal> findByCustomerIdAndStatus(String customerId, OrderStatus status);
}
