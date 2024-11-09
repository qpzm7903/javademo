package com.example.transaction_demo.domain.service.support;

import com.example.transaction_demo.domain.model.ShoppingCart;
import com.example.transaction_demo.domain.service.ShoppingCarService;

import java.util.List;
import java.util.Optional;

public class ShoppingCarServiceImpl implements ShoppingCarService {
    @Override
    public List<ShoppingCart> listAllShoppingCart() {
        return List.of();
    }
    
    @Override
    public boolean createShoppingCart(ShoppingCart shoppingCart) {
        return false;
    }
    
    @Override
    public boolean deleteShoppingCart(String id) {
        return false;
    }
    
    @Override
    public ShoppingCart updateShoppingCartById(ShoppingCart product) {
        return null;
    }
    
    @Override
    public Optional<ShoppingCart> getById(String id) {
        return Optional.empty();
    }
}
