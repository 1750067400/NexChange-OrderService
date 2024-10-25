package com.nus.nexchange.orderservice.application.event;

public interface DomainEventPublisher {
    void publish(Object event);
}
