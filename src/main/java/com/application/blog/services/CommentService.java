package com.application.blog.services;

import com.application.blog.payloads.CommentWrapper;

public interface CommentService {

    CommentWrapper createComment(CommentWrapper commentWrapper, Integer postId);
    void deleteComment(Integer commentId);
    CommentWrapper getCommentById(Integer commentId);

    CommentWrapper updateComment(CommentWrapper commentWrapper, Integer commentId);
}
