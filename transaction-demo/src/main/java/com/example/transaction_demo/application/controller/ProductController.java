package com.example.transaction_demo.application.controller;


import com.example.transaction_demo.application.controller.convert.ProductConverter;
import com.example.transaction_demo.application.controller.dto.ProductDTO;
import com.example.transaction_demo.application.controller.vo.ProductVO;
import com.example.transaction_demo.domain.model.Product;
import com.example.transaction_demo.domain.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    
    public ProductController(ProductService productService, ProductConverter productConverter) {
        this.productService = productService;
        this.productConverter = productConverter;
    }
    
    @GetMapping("/list")
    @Transactional(readOnly = true)
    List<ProductVO> listAllProduct() {
        return productConverter.convertToVO(productService.listAllProduct());
    }
    
    @PostMapping
    @Transactional
    boolean createProduct(@RequestBody ProductDTO productDTO) {
        productService.createProduct(productConverter.convertToDO(productDTO));
        return true;
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    boolean deleteProduct(@RequestParam("id") String id) {
        return productService.deleteProduct(id);
    }
    
    @PutMapping
    @Transactional
    ProductVO updateProductById(@RequestBody ProductDTO product) {
        return productConverter.convertToVO(productService.updateProductById(productConverter.convertToDO(product)));
    }
    
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    Optional<ProductVO> getById(@RequestParam("id") String id) {
        Optional<Product> byProductId = productService.getByProductId(id);
        return byProductId.map(productConverter::convertToVO);
    }
}
