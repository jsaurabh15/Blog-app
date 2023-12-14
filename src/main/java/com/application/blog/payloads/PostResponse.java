package com.application.blog.payloads;

import lombok.Data;

import java.util.List;
@Data
public class PostResponse {

    private List<PostWrapper> content;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElements;
    private Integer totalPages;
    private boolean lastPage;

}
