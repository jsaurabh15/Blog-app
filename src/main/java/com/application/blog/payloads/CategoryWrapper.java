package com.application.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryWrapper {

    private Integer categoryId;
    @NotEmpty
    @Size(min = 3, message = "category title must be at least 3 characters !!")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 10, message = "category description must be at least 10 characters !!")
    private String categoryDescription;

}
