package com.application.blog.services.impl;

import com.application.blog.dao.CategoryDao;
import com.application.blog.dao.PostDao;
import com.application.blog.dao.UserDao;
import com.application.blog.exceptions.ResourceNotFoundException;
import com.application.blog.models.Category;
import com.application.blog.models.Post;
import com.application.blog.models.User;
import com.application.blog.payloads.PostResponse;
import com.application.blog.payloads.PostWrapper;
import com.application.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDao postDao;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public PostWrapper createPost(PostWrapper postWrapper, Integer userId, Integer categoryId) {
        User user = this.userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Category category = this.categoryDao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categpry", "category id", categoryId));

        Post post = this.modelMapper.map(postWrapper, Post.class);
        post.setPostImageName("default.png");
        post.setPostAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = this.postDao.save(post);
        return this.modelMapper.map(savedPost, PostWrapper.class);
    }

    @Override
    public PostWrapper updatePost(PostWrapper postWrapper, Integer postId) {
        Post post = this.postDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        post.setPostTitle(postWrapper.getPostTitle());
        post.setPostContent(postWrapper.getPostContent());
        post.setPostImageName(postWrapper.getPostImageName());

        Post updatedPost = this.postDao.save(post);
        return this.modelMapper.map(updatedPost, PostWrapper.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        this.postDao.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost= this.postDao.findAll(p);
        List<Post> allPosts = pagePost.getContent();

        List<PostWrapper> postWrappers = new ArrayList<>();
        for(Post post: allPosts) {
            PostWrapper postWrapper = this.modelMapper.map(post, PostWrapper.class);
            postWrappers.add(postWrapper);
        }

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postWrappers);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostWrapper getPostById(Integer postId) {
        Post post = this.postDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        return this.modelMapper.map(post, PostWrapper.class);
    }

    @Override
    public List<PostWrapper> getPostsByCategory(Integer categoryId) {

        Category category = this.categoryDao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> posts = this.postDao.findByCategory(category);
        List<PostWrapper> postWrappers = new ArrayList<>();
        for(Post post: posts) {
            PostWrapper postWrapper = this.modelMapper.map(post, PostWrapper.class);
            postWrappers.add(postWrapper);
        }

        return postWrappers;
    }

    @Override
    public List<PostWrapper> getPostsByUser(Integer userId) {
        User user = this.userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> posts = this.postDao.findByUser(user);
        List<PostWrapper> postWrappers = new ArrayList<>();
        for(Post post : posts) {
            PostWrapper postWrapper = this.modelMapper.map(post, PostWrapper.class);
            postWrappers.add(postWrapper);
        }
        return postWrappers;
    }

    @Override
    public List<PostWrapper> searchPostsByTitle(String keyword) {
        List<Post> posts = this.postDao.findByPostTitleContaining(keyword);
        List<PostWrapper> postWrappers = new ArrayList<>();
        for(Post post: posts) {
            PostWrapper postWrapper = this.modelMapper.map(post, PostWrapper.class);
            postWrappers.add(postWrapper);
        }
        return postWrappers;
    }
}
