package com.example.transaction_demo.domain.service;

import com.example.transaction_demo.domain.model.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    List<Product> listAllProduct();
    
    @Transactional
    boolean createProduct(@Param("product") Product product);
}
