package com.example.transaction_demo.application.controller.convert;

import com.example.transaction_demo.application.controller.dto.ProductDTO;
import com.example.transaction_demo.application.controller.vo.ProductVO;
import com.example.transaction_demo.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductConverter {
    
    public List<ProductVO> convertToVO(List<Product> products) {
        return products.stream()
                .map(this::convertToVO)
                .toList();
    }
    
    public Product convertToDO(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .quantityInStock(productDTO.getQuantityInStock())
                .description(productDTO.getDescription())
                .build();
    }
    
    public ProductVO convertToVO(Product product) {
        
        return ProductVO.builder()
                .id(product.getId())
                .name(product.getName())
                .quantityInStock(product.getQuantityInStock())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
