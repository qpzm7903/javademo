package com.example.transaction_demo.domain.repository;

import com.example.transaction_demo.domain.model.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface ProductRepo {
    List<Product> listAllProduct();
    
    @Transactional
    boolean createProduct(@Param("product") Product product);
    
    @Delete("DELETE FROM mall_product WHERE id = #{productId}")
    boolean deleteById(@Param("productId") String productId);
    
    int updateProductById(@Param("product") Product product);
}
