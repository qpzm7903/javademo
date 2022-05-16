package com.qpzm7903.springmybatisdemo.mapper;

import com.qpzm7903.springmybatisdemo.entity.Article;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleMapperTransactionTest {
    Log logger = LogFactory.getLog(ArticleMapperTransactionTest.class);
    @Autowired
    ArticleMapper articleMapper;

    @Test
    @Order(1)
    public void whenRecordsInDatabase_shouldReturnArticleWithGivenId() {
        Article article = articleMapper.getArticle(1L);

        Assertions.assertThat(article).isNotNull();
        Assertions.assertThat(article.getId()).isEqualTo(1L);
        Assertions.assertThat(article.getAuthor()).isEqualTo("Baeldung");
        Assertions.assertThat(article.getTitle()).isEqualTo("Working with MyBatis in Spring");
    }

    @Test
    @Order(2)
    @Transactional
    public void test_insert_duplicated_article() {
        Article article = articleMapper.getArticle(1L);
        article.setTitle("2");
        articleMapper.save(article);
        org.junit.jupiter.api.Assertions.assertEquals(2, articleMapper.listArticle().size());
    }

    @Test
    @Order(3)
    public void test_transaction() {
        Article article = articleMapper.getArticle(2L);
        assert article == null;
    }
}