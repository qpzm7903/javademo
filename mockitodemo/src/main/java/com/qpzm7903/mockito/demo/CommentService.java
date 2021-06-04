package com.qpzm7903.mockito.demo;

public interface CommentService {

    Comment createComment(Comment comment);

    Comment updateComment(Comment comment);

    Comment getCommentById(Long commentId);

    boolean deleteComment(Long commentId);

}
