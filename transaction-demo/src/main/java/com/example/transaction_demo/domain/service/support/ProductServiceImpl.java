package com.example.transaction_demo.domain.service.support;

import ch.qos.logback.core.util.StringUtil;
import com.example.transaction_demo.domain.model.Product;
import com.example.transaction_demo.domain.repository.ProductRepo;
import com.example.transaction_demo.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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
    public boolean createProduct(Product newProduct) {
        boolean result = productRepo.createProduct(newProduct);
        if (newProduct.getName()
                .contains("error")) {
            throw new RuntimeException("product name is error, can not create");
        }
        return result;
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
    
    @Override
    public Optional<Product> getByProductId(String productId) {
        return productRepo.getById(productId);
    }
}
