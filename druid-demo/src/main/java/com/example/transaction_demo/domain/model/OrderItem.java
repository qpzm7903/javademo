package com.example.transaction_demo.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private String id;
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
