package com.nexchange.order.infrastructure.persistence;

import com.nexchange.order.domain.aggregate.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {
    // Additional query methods can be defined here if needed
}