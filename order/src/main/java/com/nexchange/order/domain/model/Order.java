package com.nexchange.order.domain.model;

import java.math.BigDecimal;
import java.util.List;

import com.nexchange.order.domain.aggregate.PaymentInfo;
import com.nexchange.order.domain.aggregate.PaymentStatus;

public class Order {

    private String orderId;
    private String customerId;
    private String sellerId;
    private List<Product> products;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private PaymentInfo paymentInfo;  // 支付信息


    // 构造方法
    public Order(String orderId, String customerId, String sellerId, List<Product> products) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.products = products;
        this.totalAmount = calculateTotalAmount();
        this.status = OrderStatus.PENDING;  // 初始状态为待处理
    }


    public void completeOrder() {
        if (this.paymentInfo != null && this.paymentInfo.getPaymentStatus() == PaymentStatus.PAID) {
        this.status = OrderStatus.COMPLETED;  // 支付完成后订单完成
        } else {
        throw new IllegalStateException("Order cannot be completed unless payment is made");
        }
    }


    // 计算总金额
    private BigDecimal calculateTotalAmount() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Getter 和 Setter 方法
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
    
}
