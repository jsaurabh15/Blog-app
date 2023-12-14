package com.application.blog.services;

import com.application.blog.payloads.PostResponse;
import com.application.blog.payloads.PostWrapper;

import java.util.List;

public interface PostService {

    //Create Post
    PostWrapper createPost(PostWrapper postWrapper, Integer userId, Integer categoryId);
    //Update Post
    PostWrapper updatePost(PostWrapper postWrapper, Integer postId);
    //Delete Post
    void deletePost(Integer postId);
    //Get All Post
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    //Get Post
    PostWrapper getPostById(Integer postId);
    //Get Posts By Category
    List<PostWrapper> getPostsByCategory(Integer categoryId);
    //Get Posts By User
    List<PostWrapper> getPostsByUser(Integer userId);
    //search posts
    List<PostWrapper> searchPostsByTitle(String keyword);


}
