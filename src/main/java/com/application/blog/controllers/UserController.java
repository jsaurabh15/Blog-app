package com.application.blog.controllers;

import com.application.blog.payloads.ApiResponse;
import com.application.blog.payloads.UserWrapper;
import com.application.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    // Update User
    @PutMapping("/update_user/{userId}")
    public ResponseEntity<UserWrapper> updateUser(@Valid @RequestBody UserWrapper userWrapper, @PathVariable Integer userId) {
        UserWrapper updatedUserWrapper = this.userService.updateUser(userWrapper, userId);
        return new ResponseEntity<>(updatedUserWrapper, HttpStatus.OK);
    }

    // Delete User
    @DeleteMapping("/delete_user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }
    //Get User
    @GetMapping("/get_user/{userId}")
    public ResponseEntity<UserWrapper> getUserById(@PathVariable Integer userId) {
        UserWrapper userWrapper = this.userService.getUserById(userId);
        return new ResponseEntity<>(userWrapper, HttpStatus.OK);
    }

    //Get All Users
    @GetMapping("/get_users")
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        List<UserWrapper> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
