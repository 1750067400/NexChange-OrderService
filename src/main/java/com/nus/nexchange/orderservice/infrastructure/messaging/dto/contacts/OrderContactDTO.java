package com.nus.nexchange.orderservice.infrastructure.messaging.dto.contacts;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderContactDTO {
    private UUID contactId;

    private String contactName;

    private String contactAddress;

    private String postalCode;

    private String contactNumber;

    private UUID orderId;

    private UUID userId;
}
