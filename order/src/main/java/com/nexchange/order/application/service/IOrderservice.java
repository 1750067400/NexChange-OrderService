package com.nexchange.order.application.service;

import java.util.List;

public interface IOrderservice {
    String createProposal();
    String acceptProposal(String proposalId);
    String cancelProposal(String proposalId);
    List<String> getCustomerOrderHistory(String customerId);
    String getPaymentStatus(String orderId);
}