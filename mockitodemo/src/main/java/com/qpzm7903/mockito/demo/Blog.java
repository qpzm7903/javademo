package com.qpzm7903.mockito.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-04 08:26
 */

public class Blog {
    private String title;
    private String content;
    private List<Comment> comments;
    private User owner;
    public void setOwner(User user) {
        this.owner = user;

    }

    public void setUserComment(User user, Comment comment) {
        if (Objects.isNull(comments)) {
            this.comments = new ArrayList<>();
        }
        comment.setUser(user);
        this.comments.add(comment);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getOwner() {
        return owner;
    }
}
