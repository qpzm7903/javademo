package com.example.transaction_demo.application.controller.vo;

import com.example.transaction_demo.domain.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartVO {
    private String id;
    private List<OrderItem> items;
}
