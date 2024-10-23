package com.nus.nexchange.orderservice.api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class BuyerDetailDTO {
    private UUID buyerId;

    private UUID refUserId;

    private String buyerName;

    private String buyerAddress;

    private String buyerPostalCode;

    private String buyerContactNumber;
}
