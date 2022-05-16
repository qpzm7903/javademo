package com.qpzm7903.springmybatisdemo.mapper;

import com.qpzm7903.springmybatisdemo.entity.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    @Select("SELECT * FROM ARTICLES WHERE id = #{id}")
    Article getArticle(@Param("id") Long id);

    @Select("select * from articles")
    List<Article> listArticle();

    @Insert("insert into ARTICLES(title,author) VALUES(#{title},#{author})")
    void save(Article article);
}
