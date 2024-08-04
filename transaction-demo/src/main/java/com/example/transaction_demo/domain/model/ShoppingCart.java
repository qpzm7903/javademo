package com.example.transaction_demo.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingCart {
    private String id;
    private List<OrderItem> items;
}
