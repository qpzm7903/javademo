package com.example.transaction_demo.domain.service;

import com.example.transaction_demo.domain.model.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> listAllProduct();
    
    @Transactional
    boolean createProduct(Product product);
    
    @Transactional
    boolean deleteProduct(String productId);
    
    @Transactional
    Product updateProductById(Product product);
    
    @Transactional(readOnly = true)
    Optional<Product> getByProductId(String productId);
}
