package com.qpzm7903.springmybatisdemo.controller;

import com.qpzm7903.springmybatisdemo.entity.Article;
import com.qpzm7903.springmybatisdemo.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-12 09:40
 */

@RestController
public class ArticleController {

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping(path = "/list/articles")
    public List<Article> listArticle() {
        return articleMapper.listArticle();
    }

    @PostMapping(path = "/articles")
    @Transactional
    public Article postArticle(@RequestBody Article article) {
        articleMapper.save(article);
        if (article.getTitle().contains("error")) {
            System.out.println("can not save a title with error");
            throw new IllegalArgumentException("title can not contain error");
        }
        return article;
    }

}
