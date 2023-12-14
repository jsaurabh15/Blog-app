package com.application.blog.controllers;

import com.application.blog.payloads.ApiResponse;
import com.application.blog.payloads.CommentWrapper;
import com.application.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentWrapper> createComment(@RequestBody CommentWrapper commentWrapper,
                @PathVariable Integer postId) {

        CommentWrapper createdComment = this.commentService.createComment(commentWrapper, postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment deleted successfully", true), HttpStatus.OK);
    }
    @PutMapping("/update_comment/{commentId}")
    public ResponseEntity<CommentWrapper> updateComment(@RequestBody CommentWrapper commentWrapper, @PathVariable Integer commentId) {
        CommentWrapper updatedComment = this.commentService.updateComment(commentWrapper, commentId);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentWrapper> getCommentById(@PathVariable Integer commentId) {
        CommentWrapper comment = this.commentService.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }


}
