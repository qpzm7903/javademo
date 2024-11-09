package com.example.transaction_demo.application.service;

import com.example.transaction_demo.domain.model.Address;
import com.example.transaction_demo.domain.model.Order;
import com.example.transaction_demo.domain.model.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingService {
    void addToCart(String carId, String productId, int quantity);
    
    void removeFromCart(Long productId);
    
    void updateCart(Long productId, int newQuantity);
    
    ShoppingCart viewCart();
    
    void clearCart();
    
    void createOrderFromCart(Address shippingAddress);
    
    List<Order> viewOrders();
    
    Order viewOrderDetails(Long orderId);
    
    void cancelOrder(Long orderId);
    
    void processPayment(Long orderId, BigDecimal amount);
}
