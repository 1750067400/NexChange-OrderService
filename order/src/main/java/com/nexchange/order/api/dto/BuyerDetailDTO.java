package com.nexchange.order.api.dto;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuyerDetailDTO {
    private UUID refBuyerContactId;
    private String BuyerName;
    private String BuyerAddress;
    private String BuyerPostalCode;
    private String BuyerContactNumber;
}
