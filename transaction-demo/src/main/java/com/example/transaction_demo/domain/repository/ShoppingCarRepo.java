package com.example.transaction_demo.domain.repository;


import com.example.transaction_demo.domain.model.ShoppingCart;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ShoppingCarRepo {
    List<ShoppingCart> listAllShoppingCart();
    
    @Transactional
    boolean createShoppingCart(@Param("ShoppingCart") ShoppingCart ShoppingCart);
    
    @Delete("DELETE FROM mall_shopping_car WHERE id = #{id}")
    boolean deleteById(@Param("id") String ShoppingCartId);
    
    @Update("")
    int updateShoppingCartById(@Param("id") ShoppingCart ShoppingCart);
    
    Optional<ShoppingCart> getById(@Param("id")String ShoppingCartId);
}
