package com.nus.nexchange.orderservice.infrastructure.repository;

import com.nus.nexchange.orderservice.domain.aggregate.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}