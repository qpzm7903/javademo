package com.example.transaction_demo.domain.repository;

import com.example.transaction_demo.TransactionDemoApplicationTests;
import com.example.transaction_demo.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

class ProductRepoTest extends TransactionDemoApplicationTests {
    
    @Autowired
    private ProductRepo productRepo;
    
    @Test
    void test_repo_is_not_null() {
        assert productRepo != null;
        List<Product> products = productRepo.listAllProduct();
        assert CollectionUtils.isEmpty(products);
    }
    
    @Test
    void test_create_and_list() {
        Product product = new Product();
        product.setId("test");
        
        boolean product1 = productRepo.createProduct(product);
        assert  product1;
        List<Product> products = productRepo.listAllProduct();
        assert !CollectionUtils.isEmpty(products);
        
    }
    
}