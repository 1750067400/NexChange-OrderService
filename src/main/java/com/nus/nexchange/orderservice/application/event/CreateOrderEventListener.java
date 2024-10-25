package com.nus.nexchange.orderservice.application.event;

import com.nus.nexchange.orderservice.domain.aggregate.Order;
import com.nus.nexchange.orderservice.domain.entity.OrderStatus;
import com.nus.nexchange.orderservice.infrastructure.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class CreateOrderEventListener {
    private final OrderRepository orderRepository;

    private final ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    public CreateOrderEventListener(OrderRepository orderRepository, ThreadPoolTaskScheduler taskScheduler) {
        this.orderRepository = orderRepository;
        this.taskScheduler = taskScheduler;
    }

    @EventListener
    public void handleOrderCreatedEvent(Order order) {
        System.out.println(LocalDateTime.now());
        Instant triggerTime = ZonedDateTime.of(LocalDateTime.now().plusMinutes(15), ZoneId.systemDefault()).toInstant();
        taskScheduler.schedule(() -> checkOrderStatus(order),
                triggerTime);
    }

    private void checkOrderStatus(Order order) {
        Order currentOrder = orderRepository.findById(order.getOrderId()).orElse(null);
        if (currentOrder != null && OrderStatus.UNPAID.equals(currentOrder.getOrderStatus())) {
            currentOrder.setOrderStatus(OrderStatus.EXPIRED);
            orderRepository.save(currentOrder);
        }
    }
}
