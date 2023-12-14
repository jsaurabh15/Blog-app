package com.application.blog.services.impl;

import com.application.blog.dao.CommentDao;
import com.application.blog.dao.PostDao;
import com.application.blog.exceptions.ResourceNotFoundException;
import com.application.blog.models.Comment;
import com.application.blog.models.Post;
import com.application.blog.payloads.CommentWrapper;
import com.application.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentWrapper createComment(CommentWrapper commentWrapper, Integer postId) {
        Post post = this.postDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = this.modelMapper.map(commentWrapper, Comment.class);
        comment.setPost(post);

        Comment savedComment = this.commentDao.save(comment);

        return this.modelMapper.map(savedComment, CommentWrapper.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentDao.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

        this.commentDao.delete(comment);

    }

    @Override
    public CommentWrapper getCommentById(Integer commentId) {
       Comment comment = this.commentDao.findById(commentId)
               .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

       return this.modelMapper.map(comment, CommentWrapper.class);
    }

    @Override
    public CommentWrapper updateComment(CommentWrapper commentWrapper, Integer commentId) {
        Comment comment = this.commentDao.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

        comment.setContent(commentWrapper.getContent());
        Comment updatedComment = this.commentDao.save(comment);

        return this.modelMapper.map(updatedComment, CommentWrapper.class);
    }
}
