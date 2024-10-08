package com.nexchange.order.infrastructure.persistence;

import com.nexchange.order.domain.model.Order;
import com.nexchange.order.domain.model.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    
    // 通过客户 ID 查找所有订单
    List<Order> findByCustomerId(String customerId);
    
    // 通过客户 ID 和订单状态查找订单
    List<Order> findByCustomerIdAndStatus(String customerId,OrderStatus status);
    
    // 可以根据业务需求添加更多自定义查询方法
}
