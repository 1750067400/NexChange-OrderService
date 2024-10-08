package com.nexchange.order.domain.model;

import java.math.BigDecimal;

public class Product {
    private String productId;
    private String productName;
    private BigDecimal price;

    // 构造方法
    public Product(String productId, String productName, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    // Getter 和 Setter 方法
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
