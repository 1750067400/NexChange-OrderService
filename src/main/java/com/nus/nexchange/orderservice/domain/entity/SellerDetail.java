package com.nus.nexchange.orderservice.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class SellerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sellerId;

    private UUID refUserId;

    private String sellerName;

    private String sellerAvatarURL;

//    private String sellerAddress;
//
//    private String sellerPostalCode;
//
//    private String sellerContactNumber;
}
