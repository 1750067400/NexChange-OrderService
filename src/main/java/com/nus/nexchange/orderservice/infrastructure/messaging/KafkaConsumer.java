package com.nus.nexchange.orderservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.nexchange.orderservice.application.command.RedisOrderService;
import com.nus.nexchange.orderservice.infrastructure.messaging.dto.OrderPostDTO;
import com.nus.nexchange.orderservice.infrastructure.messaging.dto.contacts.OrderContactDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private RedisOrderService redisOrderService;

    @KafkaListener(topics = "OrderBuyer")
    @Transactional
    public void orderBuyerListen(String message) {
        try{
            OrderContactDTO orderContactDTO =new ObjectMapper().readValue(message, OrderContactDTO.class);
            redisOrderService.storeOrderContact(orderContactDTO);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @KafkaListener(topics="OrderPost")
    @Transactional
    public void orderPostListen(String message) {
        try{
            OrderPostDTO orderPostDTO =new ObjectMapper().readValue(message, OrderPostDTO.class);
            redisOrderService.storeOrderPost(orderPostDTO);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
