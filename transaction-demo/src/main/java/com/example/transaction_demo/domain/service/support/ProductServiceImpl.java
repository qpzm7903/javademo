package com.example.transaction_demo.domain.service.support;

import com.example.transaction_demo.domain.model.Product;
import com.example.transaction_demo.domain.repository.ProductRepo;
import com.example.transaction_demo.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> listAllProduct() {
        return productRepo.listAllProduct();
    }
    
    @Override
    @Transactional
    public boolean createProduct(Product product) {
        return productRepo.createProduct(product);
    }
    
    @Override
    public boolean deleteProduct(String productId) {
        return productRepo.deleteById(productId);
    }
    
    @Override
    public Product updateProductById(Product product) {
        int count = productRepo.updateProductById(product);
        if (count == 1) {
            return product;
        }
        throw new RuntimeException("update product failed");
    }
}
