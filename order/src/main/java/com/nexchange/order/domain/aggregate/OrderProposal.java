package com.nexchange.order.domain.aggregate;

import com.nexchange.order.domain.model.Product;

import com.nexchange.order.domain.model.OrderStatus;
import java.math.BigDecimal;

public class OrderProposal {

    private String proposalId;
    private String customerId;
    private String sellerId;
    private Product product;  // 单一产品
    private BigDecimal totalAmount;
    private OrderStatus status;
    private PaymentInfo paymentInfo;  // 支付信息

    // 构造方法
    public OrderProposal(String customerId, String sellerId, Product product) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.product = product;
        this.totalAmount = product.getPrice();  // 直接使用产品的价格作为总金额
        this.status = OrderStatus.PROPOSAL;  // 初始状态为提案
    }

    // 接受提案，将提案转换为订单
    public void acceptProposal() {
        if (this.status == OrderStatus.PROPOSAL) {
            this.status = OrderStatus.ORDER;  // 更改状态为订单
        } else {
            throw new IllegalStateException("Cannot accept proposal that is not in PROPOSAL state");
        }
    }

    // 取消提案
    public void cancelProposal() {
        if (this.status == OrderStatus.PROPOSAL) {
            this.status = OrderStatus.CANCELLED;  // 更改状态为取消
        } else {
            throw new IllegalStateException("Cannot cancel proposal that is not in PROPOSAL state");
        }
    }

    // Getter 和 Setter 方法
    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.totalAmount = product.getPrice();  // 当产品变化时，更新总金额
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
