package com.application.blog.controllers;

import com.application.blog.config.AppConstants;
import com.application.blog.payloads.ApiResponse;
import com.application.blog.payloads.PostResponse;
import com.application.blog.payloads.PostWrapper;
import com.application.blog.services.FileService;
import com.application.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    //Create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostWrapper> createPost(@Valid @RequestBody PostWrapper postWrapper,
              @PathVariable Integer userId, @PathVariable Integer categoryId) {

        PostWrapper createPost = this.postService.createPost(postWrapper, userId, categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostWrapper>> getPostsByUser(@PathVariable Integer userId) {
        List<PostWrapper> userPosts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(userPosts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostWrapper>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostWrapper> postsUnderCategory = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postsUnderCategory, HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
            ) {
        PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPosts,HttpStatus.OK);
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostWrapper> getPostById(@PathVariable Integer postId) {
        PostWrapper post = this.postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/delete_post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post is deleted successfully", true), HttpStatus.OK);
    }
    @PutMapping("/update_post/{postId}")
    public ResponseEntity<PostWrapper> updatePost(@RequestBody PostWrapper postWrapper, @PathVariable Integer postId) {
        PostWrapper updatedPostWrapper = this.postService.updatePost(postWrapper, postId);
        return new ResponseEntity<>(updatedPostWrapper,HttpStatus.OK);
    }
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostWrapper>> searchPostsByTitle(@PathVariable String keywords) {
        List<PostWrapper> searchPosts = this.postService.searchPostsByTitle(keywords);
        return new ResponseEntity<>(searchPosts, HttpStatus.OK);
    }
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostWrapper> uploadPostImage(@RequestParam("image") MultipartFile image,
                 @PathVariable Integer postId) throws IOException {
        PostWrapper postWrapper = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postWrapper.setPostImageName(fileName);
        PostWrapper updatedPost = this.postService.updatePost(postWrapper, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
