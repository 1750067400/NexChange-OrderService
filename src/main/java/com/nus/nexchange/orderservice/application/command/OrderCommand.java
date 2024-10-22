package com.nus.nexchange.orderservice.application.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.api.dto.UUIDOrderDTO;
import com.nus.nexchange.orderservice.domain.aggregate.Order;
import com.nus.nexchange.orderservice.domain.entity.BuyerDetail;
import com.nus.nexchange.orderservice.domain.entity.OrderStatus;
import com.nus.nexchange.orderservice.domain.entity.SellerDetail;
import com.nus.nexchange.orderservice.infrastructure.messaging.KafkaProducer;
import com.nus.nexchange.orderservice.infrastructure.messaging.dto.UserOrderDTO;
import com.nus.nexchange.orderservice.infrastructure.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderCommand implements IOrderCommand {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public void createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setSellerDetail(modelMapper.map(orderDTO.getSellerDetail(), SellerDetail.class));
        order.setBuyerDetail(modelMapper.map(orderDTO.getBuyerDetail(), BuyerDetail.class));
        order.setOrderStatus(OrderStatus.UNPAID);

        orderRepository.save(order);

        orderDTO.setOrderId(order.getOrderId());

        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setUserId(order.getBuyerDetail().getRefUserId());

        try {
            String orderDTOJson = new ObjectMapper().writeValueAsString(modelMapper.map(orderDTO, UserOrderDTO.class));
            kafkaProducer.sendMessage("CreatedOrder", orderDTOJson);

            String postId = String.valueOf(orderDTO.getRefPostId());
            kafkaProducer.sendMessage("PostFinished",postId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UUIDOrderDTO expireOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setOrderStatus(OrderStatus.EXPIRED);

        orderRepository.save(order);

        UUIDOrderDTO UUIDOrderDTO = new UUIDOrderDTO();
        UUIDOrderDTO.setOrderId(order.getOrderId());
        UUIDOrderDTO.setUserId(order.getBuyerDetail().getRefUserId());
        UUIDOrderDTO.setPostId(order.getRefPostId());

        return UUIDOrderDTO;
    }

    @Override
    public void payOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setOrderStatus(OrderStatus.PAID);

        orderRepository.save(order);
    }

    @Override
    public void shipOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setOrderStatus(OrderStatus.SHIPPED);

        orderRepository.save(order);
    }

    @Override
    public UUIDOrderDTO cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setOrderStatus(OrderStatus.CANCELED);

        orderRepository.save(order);

        UUIDOrderDTO UUIDOrderDTO = new UUIDOrderDTO();
        UUIDOrderDTO.setOrderId(order.getOrderId());
        UUIDOrderDTO.setUserId(order.getBuyerDetail().getRefUserId());
        UUIDOrderDTO.setPostId(order.getRefPostId());

        return UUIDOrderDTO;
    }

    @Override
    public void completeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);

        orderRepository.save(order);
    }
}