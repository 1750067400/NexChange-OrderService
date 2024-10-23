package com.nus.nexchange.orderservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.nexchange.orderservice.application.command.RedisOrderService;
import com.nus.nexchange.orderservice.infrastructure.messaging.dto.OrderPostDTO;
import com.nus.nexchange.orderservice.infrastructure.messaging.dto.contacts.OrderContactDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final RedisOrderService redisOrderService;

    @Autowired
    public KafkaConsumer(RedisOrderService redisOrderService) {
        this.redisOrderService = redisOrderService;
    }

    @KafkaListener(topics = "OrderBuyer")
    @Transactional
    public void orderBuyerListen(String message) {
        try {
            OrderContactDTO orderContactDTO = new ObjectMapper().readValue(message, OrderContactDTO.class);
            redisOrderService.storeOrderContact(orderContactDTO);
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "OrderPost")
    @Transactional
    public void orderPostListen(String message) {
        try {
            OrderPostDTO orderPostDTO = new ObjectMapper().readValue(message, OrderPostDTO.class);
            redisOrderService.storeOrderPost(orderPostDTO);
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
        }
    }

}
