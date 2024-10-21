package com.nus.nexchange.orderservice.application.command;

import com.nus.nexchange.orderservice.api.dto.BuyerDetailDTO;
import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.api.dto.SellerDetailDTO;
import com.nus.nexchange.orderservice.domain.entity.OrderStatus;
import com.nus.nexchange.orderservice.infrastructure.messaging.dto.OrderPostDTO;
import com.nus.nexchange.orderservice.infrastructure.messaging.dto.PostSellerDTO;
import com.nus.nexchange.orderservice.infrastructure.messaging.dto.contacts.OrderContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisOrderService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderCommand orderCommand;

    public void storeOrderContact(OrderContactDTO orderContactDTO) {
        String key = "order:" + orderContactDTO.getSecret();
        redisTemplate.opsForHash().put(key, "buyerInfo", orderContactDTO);
        checkAndCreateOrder(orderContactDTO.getSecret());
    }

    public void storeOrderPost(OrderPostDTO orderPostDTO) {
        String key = "order:" + orderPostDTO.getSecret();
        redisTemplate.opsForHash().put(key, "postInfo", orderPostDTO);
        checkAndCreateOrder(orderPostDTO.getSecret());
    }

    private void checkAndCreateOrder(UUID secret) {
        String key = "order:" + secret;
        String lockKey = key + ":lock";

        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", 10, TimeUnit.SECONDS);

        if (Boolean.TRUE.equals(isLocked)) {
            try {
                OrderContactDTO orderContactDTO = (OrderContactDTO) redisTemplate.opsForHash().get(key, "buyerInfo");
                OrderPostDTO orderPostDTO = (OrderPostDTO) redisTemplate.opsForHash().get(key, "postInfo");

                if (orderContactDTO != null && orderPostDTO != null) {
                    System.out.println("trigger");
                    OrderDTO orderDTO = convertOrderDTO(orderContactDTO, orderPostDTO);
                    orderCommand.createOrder(orderDTO);
                }
            } finally {
                redisTemplate.delete(key);

            }
        }
    }

    private OrderDTO convertOrderDTO(OrderContactDTO orderContactDTO, OrderPostDTO orderPostDTO) {
        OrderDTO orderDTO = new OrderDTO();

//        orderDTO.setOrderId(orderContactDTO.getOrderId());

        orderDTO.setRefPostId(orderPostDTO.getPostId());
        orderDTO.setRefPostTitle(orderPostDTO.getPostTittle());
        orderDTO.setRefPostShortcutURL(orderPostDTO.getPostShortcutURL());
        orderDTO.setRefPostPrice(orderPostDTO.getPostPrice());

        PostSellerDTO postSellerDTO = orderPostDTO.getPostSeller();
        SellerDetailDTO sellerDetailDTO = new SellerDetailDTO();
        sellerDetailDTO.setSellerName(postSellerDTO.getSellerName());
        sellerDetailDTO.setSellerAvatarURL(postSellerDTO.getSellerAvatarURL());
        sellerDetailDTO.setRefUserId(orderPostDTO.getUserId());
        orderDTO.setSellerDetail(sellerDetailDTO);

        BuyerDetailDTO buyerDetailDTO = new BuyerDetailDTO();
        buyerDetailDTO.setRefUserId(orderContactDTO.getUserId());
        buyerDetailDTO.setBuyerName(orderContactDTO.getContactName());
        buyerDetailDTO.setBuyerAddress(orderContactDTO.getContactAddress());
        buyerDetailDTO.setBuyerContactNumber(orderContactDTO.getContactNumber());
        buyerDetailDTO.setBuyerPostalCode(orderContactDTO.getPostalCode());
        orderDTO.setBuyerDetail(buyerDetailDTO);

        orderDTO.setOrderStatus(OrderStatus.UNPAID);
        orderDTO.setUserId(orderContactDTO.getUserId());

        return orderDTO;
    }
}
