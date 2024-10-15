package com.nexchange.order.domain.aggregate;

import com.nexchange.order.domain.entity.BuyerDetail;
import com.nexchange.order.domain.entity.OrderStatus;
import com.nexchange.order.domain.entity.SellerDetail;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    private UUID refPostId;

    private String refPostTitle;

    private String refPostShortcut;

    private double refPostPrice;

    private OrderStatus orderStatus;

    @CreationTimestamp
    private LocalDateTime dateTimeCreated;

    @UpdateTimestamp
    private LocalDateTime dateTimeUpdated;

    @OneToOne
    private SellerDetail sellerDetail;

    @OneToOne
    private BuyerDetail buyerDetail;
}