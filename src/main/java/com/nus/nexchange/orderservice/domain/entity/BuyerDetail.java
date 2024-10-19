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
public class BuyerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID buyerId;

    private UUID refUserId;

    private String buyerName;

    private String buyerAddress;

    private String buyerPostalCode;

    private String buyerContactNumber;
}
