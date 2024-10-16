package com.nexchange.order.api.dto;


import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class OrderDTO {
    private UUID orderId;
    private UUID refPostId;
    private UUID refSellerId;
    private UUID refBuyerId;
    private String refPostTitle;
    private String refPostShortcut;
    private double refPostPrice;
    private String orderStatus; 
    private LocalDateTime datetimeCreated;
    private LocalDateTime datetimeUpdated;
    private SellerDetailDTO sellerDetail;
    private BuyerDetailDTO buyerDetail;
    
    public UUID getRefPostId() {
        return refPostId;
    }
}
