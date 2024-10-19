package com.nus.nexchange.orderservice.infrastructure.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "Login")
    public void listen(String message) {
        System.out.println(message);
    }

}
