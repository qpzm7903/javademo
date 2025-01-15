package com.example.demo.dao.support;

import com.example.demo.dao.AuthorDao;
import com.example.demo.entity.Author;
import org.springframework.stereotype.Component;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-12-5:41
 */
@Component
public class DefaultAuthorDao implements AuthorDao {
    @Override
    public Author getById(String id) {
        return null;
    }
}
