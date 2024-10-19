package com.nus.nexchange.orderservice.api.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BuyerDetailDTO {
    private UUID refUserId;

    private String buyerName;

    private String buyerAddress;

    private String buyerPostalCode;

    private String buyerContactNumber;

    private UUID orderId;
}
