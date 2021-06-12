package com.qpzm7903.springmybatisdemo.mapper;

import com.qpzm7903.springmybatisdemo.config.PersistenceConfig;
import com.qpzm7903.springmybatisdemo.entity.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class ArticleMapperTest {

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
}