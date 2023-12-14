package com.application.blog.payloads;

import lombok.Data;

@Data
public class CommentWrapper {

    private Integer commentId;

    private String content;
}
