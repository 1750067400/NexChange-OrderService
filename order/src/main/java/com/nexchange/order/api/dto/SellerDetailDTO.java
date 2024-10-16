package com.nexchange.order.api.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SellerDetailDTO {
    private UUID refSellerContactId;
    private String sellerName;
    private String sellerAddress;
    private String sellerPostalCode;
    private String sellerContactNumber;
}