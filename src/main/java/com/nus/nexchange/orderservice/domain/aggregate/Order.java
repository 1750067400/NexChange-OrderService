package com.nus.nexchange.orderservice.domain.aggregate;

import com.nus.nexchange.orderservice.domain.entity.BuyerDetail;
import com.nus.nexchange.orderservice.domain.entity.OrderStatus;
import com.nus.nexchange.orderservice.domain.entity.SellerDetail;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@NoArgsConstructor
@Table(name = "nexorder")
public class Order {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    private UUID refPostId;

    private String refPostTitle;

    private String refPostShortcutURL;

    private BigDecimal refPostPrice;

    private OrderStatus orderStatus;

    @CreationTimestamp
    private LocalDateTime dateTimeCreated;

    @UpdateTimestamp
    private LocalDateTime dateTimeUpdated;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private SellerDetail sellerDetail;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BuyerDetail buyerDetail;
}