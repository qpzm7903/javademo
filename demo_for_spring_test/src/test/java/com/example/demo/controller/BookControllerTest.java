package com.example.demo.controller;

import com.example.demo.config.MyProperties;
import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-11-23:04
 */
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private MyProperties myProperties;

    @MockBean
    private BookDao bookDao;


    @Test
    void test_context() {
        assert bookController != null;
        assert myProperties != null;

        assert myProperties.getSecurity() != null;
    }

    @Test
    void test_get_by_id(@Autowired MockMvc mvc) throws Exception {
        Book book = new Book();
        book.setId("1");
        book.setName("how to read");
        book.setAuthor("mock author");
        book.setDescription("mock desc");
        book.setPrice("0");
        when(bookDao.getById("1")).thenReturn(Optional.of(book));
        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":\"1\",\"name\":\"how to read\",\"author\":\"mock author\",\"price\":\"0\",\"description\":\"mock desc\"}"));

    }


}