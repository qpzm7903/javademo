package com.example.transaction_demo.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Order {
    private String id;
    private String orderNumber;
    private Date orderDate;
    private OrderStatus status;
    private List<OrderItem> items;
    private Address shippingAddress;
    private BigDecimal totalPrice;
}
