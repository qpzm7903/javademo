package com.example.demo.dao.support;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    private ObjectMapper objectMapper;

    public DefaultBookDao() {
        Book book = new Book();
        book.setId("1");
        book.setName("how to read");
        book.setAuthor("god");
        book.setDescription("god teach you how to read");
        book.setPrice("0");
        repo.put("1", book);
    }

    @Autowired
    private RedisTemplate<String, String> template;

    @Override
    public Optional<Book> getById(String id) {
        String s = template.opsForValue().get(id);

        if (ObjectUtils.isEmpty(s)) {

            return Optional.ofNullable(repo.get(id));
        }
        try {
            return Optional.of(objectMapper.readValue(s, Book.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
