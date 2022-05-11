package com.example.demo.dao.support;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-12-5:41
 */
@Component
public class DefaultBookDao implements BookDao {
    Map<String, Book> repo = new HashMap<>();

    public DefaultBookDao() {
        Book book = new Book();
        book.setId("1");
        book.setName("how to read");
        book.setAuthor("god");
        book.setDescription("god teach you how to read");
        book.setPrice("0");
        repo.put("1", book);
    }

    @Override
    public Optional<Book> getById(String id) {
        return Optional.ofNullable(repo.get(id));
    }
}
