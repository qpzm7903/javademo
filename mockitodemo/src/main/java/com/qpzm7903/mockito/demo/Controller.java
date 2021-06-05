package com.qpzm7903.mockito.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-03 22:51
 */

public class Controller {
    private final BlogService blogService;
    private final CommentService commentService;
    private final UserService userService;

    public Controller(BlogService blogService, CommentService commentService, UserService userService) {
        this.blogService = blogService;
        this.commentService = commentService;
        this.userService = userService;
    }

    public Blog createBlog(Blog blog, User user) {
        boolean exist = userService.checkUserExist(user);
        if (!exist) {
            return null;
        }
        blog.setOwner(user);
        blogService.createBlog(blog);
        return blog;
    }

    public List<Blog> getUserAllBLog(User user) {
        boolean exist = userService.checkUserExist(user);
        if (!exist) {
            return new ArrayList<>();
        }
        List<Blog> resutl = blogService.listBLogOfUser(user);
        return resutl;
    }

    public Blog comment(Blog blog, User user, Comment comment) {
        boolean exist = userService.checkUserExist(user);
        if (!exist) {
            return null;
        }
        boolean blogExist = blogService.checkBlogExist(blog);
        if (!blogExist) {
            return null;
        }
        commentService.createComment(comment);
        blog.setUserComment(user, comment);
        return blog;
    }
}
