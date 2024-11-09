package com.example.transaction_demo.domain.service;

import com.example.transaction_demo.domain.model.ShoppingCart;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ShoppingCarService {
    @Transactional(readOnly = true)
    List<ShoppingCart> listAllShoppingCart();
    
    @Transactional
    boolean createShoppingCart(ShoppingCart shoppingCart);
    
    @Transactional
    boolean deleteShoppingCart(String id);
    
    @Transactional
    ShoppingCart updateShoppingCartById(ShoppingCart product);
    
    @Transactional(readOnly = true)
    Optional<ShoppingCart> getById(String id);
}
