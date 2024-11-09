package com.example.transaction_demo.application.service;

import com.example.transaction_demo.domain.model.Address;
import com.example.transaction_demo.domain.model.Order;
import com.example.transaction_demo.domain.model.Product;
import com.example.transaction_demo.domain.model.ShoppingCart;
import com.example.transaction_demo.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShoppingServiceImpl implements ShoppingService {
    
    @Autowired
    private ProductService productService;
    @Override
    public void addToCart(String carId, String productId, int quantity) {
        // productService.
        Optional<Product> byProductId = productService.getByProductId(productId);
        if (!byProductId.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        
    }
    
    @Override
    public void removeFromCart(Long productId) {
    
    }
    
    @Override
    public void updateCart(Long productId, int newQuantity) {
    
    }
    
    @Override
    public ShoppingCart viewCart() {
        return null;
    }
    
    @Override
    public void clearCart() {
    
    }
    
    @Override
    public void createOrderFromCart(Address shippingAddress) {
    
    }
    
    @Override
    public List<Order> viewOrders() {
        return List.of();
    }
    
    @Override
    public Order viewOrderDetails(Long orderId) {
        return null;
    }
    
    @Override
    public void cancelOrder(Long orderId) {
    
    }
    
    @Override
    public void processPayment(Long orderId, BigDecimal amount) {
    
    }
}
