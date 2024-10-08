package com.nexchange.order.domain.aggregate;

import java.math.BigDecimal;

public class PaymentInfo {

    private String paymentId;  // 支付唯一标识符
    private BigDecimal amount; // 支付金额
    private PaymentStatus paymentStatus;  // 支付状态
    private String paymentMethod; // 支付方式 (例如: Credit Card, PayPal, etc.)

    // 构造方法
    public PaymentInfo(String paymentId, BigDecimal amount, PaymentStatus paymentStatus, String paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

    // Getter 和 Setter 方法
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    // 检查支付是否成功
    public boolean isPaymentSuccessful() {
        return this.paymentStatus == PaymentStatus.PAID;
    }
}
