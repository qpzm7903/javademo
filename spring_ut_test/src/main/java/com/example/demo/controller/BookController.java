package com.example.demo.controller;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-11-22:59
 */
@RestController
public class BookController implements InitializingBean {
    @Autowired
    private BookDao bookDao;

    @GetMapping("/books/{id}")
    Book getById(@PathVariable("id") String id) {
        return bookDao.getById(id).orElseThrow(()->new RuntimeException("book not found"));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("hello world");
    }
}
