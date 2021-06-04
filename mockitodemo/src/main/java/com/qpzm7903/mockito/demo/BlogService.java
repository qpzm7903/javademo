package com.qpzm7903.mockito.demo;

import java.util.List;

public interface BlogService {
    Blog createBlog(Blog blog);
    Blog getBlogById(Long blogLd);
    Blog update(Blog blog);
    boolean deleteBlog(Long blogId);
    List<Blog> listBlog();

    List<Blog> listBLogOfUser(User user);

    boolean checkBlogExist(Blog blog);
}
