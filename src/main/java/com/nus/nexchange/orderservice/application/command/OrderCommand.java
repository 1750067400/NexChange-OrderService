package com.nus.nexchange.orderservice.application.command;

import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.domain.aggregate.Order;
import com.nus.nexchange.orderservice.domain.entity.BuyerDetail;
import com.nus.nexchange.orderservice.domain.entity.OrderStatus;
import com.nus.nexchange.orderservice.domain.entity.SellerDetail;
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

    @Override
    public void createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setSellerDetail(modelMapper.map(orderDTO.getSellerDetail(), SellerDetail.class));
        order.setBuyerDetail(modelMapper.map(orderDTO.getBuyerDetail(), BuyerDetail.class));
        order.setOrderStatus(OrderStatus.UNPAID);

        orderRepository.save(order);

        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderStatus(order.getOrderStatus());
    }

    @Override
    public void expireOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setOrderStatus(OrderStatus.EXPIRED);

        orderRepository.save(order);
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
    public OrderDTO cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setOrderStatus(OrderStatus.CANCELED);

        orderRepository.save(order);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setUserId(order.getBuyerDetail().getRefUserId());

        return orderDTO;
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