package com.example.transaction_demo.application.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantityInStock;
}
