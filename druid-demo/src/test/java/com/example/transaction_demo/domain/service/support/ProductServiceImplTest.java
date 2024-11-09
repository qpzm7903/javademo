package com.example.transaction_demo.domain.service.support;

import com.example.transaction_demo.TransactionDemoApplicationTests;
import com.example.transaction_demo.domain.model.Product;
import com.example.transaction_demo.domain.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @Test
    void test_create_and_delete() {
        Product product = new Product();
        product.setId("test");
        product.setName("test product");
        product.setDescription("this is ia test product");
        product.setPrice(BigDecimal.TEN);
        
        assert productService.createProduct(product);
        List<Product> products = productService.listAllProduct();
        assert !CollectionUtils.isEmpty(products);
        
        assert productService.deleteProduct(product.getId());
        
        products = productService.listAllProduct();
        assert CollectionUtils.isEmpty(products);
    }
    
    @Test
    void test_create_and_update() {
        Product product = new Product();
        product.setId("test");
        product.setName("test product");
        product.setDescription("this is ia test product");
        product.setPrice(BigDecimal.TEN);
        
        assert productService.createProduct(product);
        List<Product> products = productService.listAllProduct();
        assert !CollectionUtils.isEmpty(products);
        product.setPrice(BigDecimal.ZERO);
        product.setName("update product name");
        product.setDescription("update product desc");
        
        Product updatedProduct = productService.updateProductById(product);
        
        assert Objects.equals(updatedProduct, product);

    }
    
    @Test
    void test_create_and_find() {
        Product product = new Product();
        product.setId("test");
        product.setName("test product");
        product.setDescription("this is ia test product");
        product.setPrice(BigDecimal.TEN);
        
        assert productService.createProduct(product);
        List<Product> products = productService.listAllProduct();
        assert !CollectionUtils.isEmpty(products);
        
        Optional<Product> byProductId = productService.getByProductId(product.getId());
        assert byProductId.isPresent();
        
        assert byProductId.get()
                .equals(product);
    }

}