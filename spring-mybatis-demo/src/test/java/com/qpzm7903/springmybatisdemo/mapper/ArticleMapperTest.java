package com.qpzm7903.springmybatisdemo.mapper;

import com.qpzm7903.springmybatisdemo.entity.Article;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ArticleMapperTest {
    Log logger = LogFactory.getLog(ArticleMapperTest.class);
    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void whenRecordsInDatabase_shouldReturnArticleWithGivenId() {
        Article article = articleMapper.getArticle(1L);

        Assertions.assertThat(article).isNotNull();
        Assertions.assertThat(article.getId()).isEqualTo(1L);
        Assertions.assertThat(article.getAuthor()).isEqualTo("Baeldung");
        Assertions.assertThat(article.getTitle()).isEqualTo("Working with MyBatis in Spring");
    }

    @Test
    public void test_insert_duplicated_article() {
        Article article = articleMapper.getArticle(1L);
        try {
            articleMapper.save(article);
        } catch (Exception e) {
            logger.error("save article exception", e);
        }
        org.junit.jupiter.api.Assertions.assertEquals(1, articleMapper.listArticle().size());
    }
}