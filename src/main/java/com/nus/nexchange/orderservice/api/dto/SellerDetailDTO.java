package com.nus.nexchange.orderservice.api.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class SellerDetailDTO {
    private UUID sellerId;

    private UUID refUserId;

    private String sellerName;

    private String sellerAddress;

    private String sellerPostalCode;

    private String sellerContactNumber;

    private UUID orderId;
}