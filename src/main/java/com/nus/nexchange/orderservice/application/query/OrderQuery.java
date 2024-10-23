package com.nus.nexchange.orderservice.application.query;

import com.nus.nexchange.orderservice.api.dto.BuyerDetailDTO;
import com.nus.nexchange.orderservice.api.dto.OrderDTO;
import com.nus.nexchange.orderservice.api.dto.SellerDetailDTO;
import com.nus.nexchange.orderservice.domain.aggregate.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nus.nexchange.orderservice.infrastructure.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrderQuery implements IOrderQuery {

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public OrderQuery(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDTO getOrderByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        return convertToOrderDTO(order);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }

        return orders.stream()
                .map(this::convertToOrderDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrdersBySellerId(UUID sellerId) {
        List<Order> orders = orderRepository.findBySellerId(sellerId);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }

        return orders.stream()
                .map(this::convertToOrderDTO)
                .toList();
    }

    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        BuyerDetailDTO buyerDetailDTO = modelMapper.map(order.getBuyerDetail(), BuyerDetailDTO.class);
        orderDTO.setBuyerDetail(buyerDetailDTO);

        SellerDetailDTO sellerDetailDTO = modelMapper.map(order.getSellerDetail(), SellerDetailDTO.class);
        orderDTO.setSellerDetail(sellerDetailDTO);

        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setUserId(buyerDetailDTO.getRefUserId());

        return orderDTO;
    }
}