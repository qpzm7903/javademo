package com.example.demo.dao;

import com.example.demo.entity.Book;

import java.util.Optional;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-12-5:39
 */
public interface BookDao{
    Optional<Book> getById(String id);
}
