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
public class OrderItemVO {
    private String id;
    private ProductVO product;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
