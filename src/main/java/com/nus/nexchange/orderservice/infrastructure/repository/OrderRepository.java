package com.nus.nexchange.orderservice.infrastructure.repository;

import com.nus.nexchange.orderservice.domain.aggregate.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("Select o From Order o Where o.buyerDetail.refUserId = :userId")
    List<Order> findByUserId(@Param("userId") UUID userId);

    @Query("Select o From Order o Where o.sellerDetail.refUserId = :sellerId")
    List<Order> findBySellerId(@Param("sellerId") UUID sellerId);
}