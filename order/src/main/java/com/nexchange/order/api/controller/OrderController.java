package com.nexchange.order.api.controller;

import com.nexchange.order.application.service.IOrderservice;
import com.nexchange.order.domain.aggregate.OrderProposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderservice orderService;

    // 创建提案
    @PostMapping("/proposal")
    public ResponseEntity<OrderProposal> createProposal(@RequestBody OrderProposal orderProposal) {
        OrderProposal createdProposal = orderService.createProposal(orderProposal);
        return new ResponseEntity<>(createdProposal, HttpStatus.CREATED);
    }

    // 接受提案并将其转换为订单
    @PostMapping("/proposal/{proposalId}/accept")
    public ResponseEntity<Void> acceptProposal(@PathVariable String proposalId) {
        orderService.acceptProposal(proposalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 取消提案
    @PostMapping("/proposal/{proposalId}/cancel")
    public ResponseEntity<Void> cancelProposal(@PathVariable String proposalId) {
        orderService.cancelProposal(proposalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查看客户的订单历史
    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<OrderProposal>> getOrderHistory(@PathVariable String customerId) {
        List<OrderProposal> orderHistory = orderService.getOrderHistory(customerId);
        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }

    // 查看客户待支付的订单
    @GetMapping("/pending/{customerId}")
    public ResponseEntity<List<OrderProposal>> getPendingOrders(@PathVariable String customerId) {
        List<OrderProposal> pendingOrders = orderService.getPendingOrders(customerId);
        return new ResponseEntity<>(pendingOrders, HttpStatus.OK);
    }
}
