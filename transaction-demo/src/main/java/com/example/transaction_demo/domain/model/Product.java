package com.example.transaction_demo.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantityInStock;
}
