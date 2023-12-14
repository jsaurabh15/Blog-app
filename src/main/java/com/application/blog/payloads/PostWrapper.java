package com.application.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostWrapper {
    private Integer postId;
    @NotEmpty
    private String postTitle;
    @NotEmpty
    private String postContent;
    private String postImageName;
    private Date postAddedDate;
    private CategoryWrapper category;
    private UserWrapper user;

    private Set<CommentWrapper> comments = new HashSet<>();

}
