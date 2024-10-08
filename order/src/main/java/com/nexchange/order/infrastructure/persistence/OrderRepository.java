package com.nexchange.order.infrastructure.persistence;

import com.nexchange.order.domain.aggregate.OrderProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderProposal, String> {

    // 根据客户 ID 查找该客户的所有提案/订单
    List<OrderProposal> findByCustomerId(String customerId);

    // 根据客户 ID 和订单状态查找客户的提案/订单
    List<OrderProposal> findByCustomerIdAndStatus(String customerId, String status);

}
