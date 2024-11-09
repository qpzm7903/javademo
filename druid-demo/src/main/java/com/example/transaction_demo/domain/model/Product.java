package com.example.transaction_demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantityInStock;
}
