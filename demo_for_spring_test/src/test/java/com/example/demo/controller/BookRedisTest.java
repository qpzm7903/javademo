package com.example.demo.controller;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-12-21:43
 */
@DataRedisTest
@SpringBootTest
public class BookRedisTest {

    @Autowired
    BookDao bookDao;

    @Test
    void test() {
        Optional<Book> byId = bookDao.getById("1");
        System.out.println(byId);
    }
}
