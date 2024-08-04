package com.example.transaction_demo.domain.service.support;

import com.example.transaction_demo.TransactionDemoApplicationTests;
import com.example.transaction_demo.domain.model.Product;
import com.example.transaction_demo.domain.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

class ProductServiceImplTest extends TransactionDemoApplicationTests {
    
    @Autowired private ProductService productService;
    
    
    @Test
    void test_repo_is_not_null() {
        assert productService != null;
        List<Product> products = productService.listAllProduct();
        assert CollectionUtils.isEmpty(products);
    }
    
    @Test
    void test_create_and_list() {
        Product product = new Product();
        product.setId("test");
        product.setName("test product");
        product.setDescription("this is ia test product");
        product.setPrice(BigDecimal.TEN);
        
        boolean product1 = productService.createProduct(product);
        assert  product1;
        List<Product> products = productService.listAllProduct();
        assert !CollectionUtils.isEmpty(products);
        
    }

}