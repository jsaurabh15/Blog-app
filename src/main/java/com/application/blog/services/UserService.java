package com.application.blog.services;

import com.application.blog.exceptions.UserAlreadyExistsException;
import com.application.blog.payloads.UserWrapper;

import java.util.List;
public interface UserService {

    UserWrapper registerNewUser(UserWrapper userWrapper);
    UserWrapper updateUser(UserWrapper userWrapper, Integer userId);
    UserWrapper getUserById(Integer userId);
    List<UserWrapper> getAllUsers();
    void deleteUser(Integer userId);
}
