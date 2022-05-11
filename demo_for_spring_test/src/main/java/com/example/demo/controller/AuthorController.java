package com.example.demo.controller;

import com.example.demo.dao.AuthorDao;
import com.example.demo.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-11-23:18
 */
@RestController
public class AuthorController {
    @Autowired
    private AuthorDao authorDao;

    @GetMapping("/authors/{id}")
    Author getById(@PathVariable("id")String id){
        Author author = new Author();
        author.setId("1");
        author.setName("god");
        return author;
    }
}
