package com.application.blog.services.impl;

import com.application.blog.config.AppConstants;
import com.application.blog.dao.RoleDao;
import com.application.blog.dao.UserDao;
import com.application.blog.exceptions.ResourceNotFoundException;
import com.application.blog.exceptions.UserAlreadyExistsException;
import com.application.blog.models.Role;
import com.application.blog.models.User;
import com.application.blog.payloads.UserWrapper;
import com.application.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServicesImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleDao roleDao;

    @Override
    public UserWrapper registerNewUser(UserWrapper userWrapper)  {
        User user = this.modelMapper.map(userWrapper, User.class);
        if (userDao.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = this.roleDao.findById(AppConstants.ROLE_USER).get();
        user.getRoles().add(role);
        User savedUser = this.userDao.save(user);
        return this.modelMapper.map(savedUser, UserWrapper.class);
    }
    @Override
    public UserWrapper updateUser(UserWrapper userWrapper, Integer userId) {

       User user = this.userDao.findById(userId)
               .orElseThrow(() -> new ResourceNotFoundException("User","id", userId));

        user.setName(userWrapper.getName());
        user.setEmail(userWrapper.getEmail());
        user.setPassword(userWrapper.getPassword());
        user.setAbout(userWrapper.getAbout());

        User updateUser = this.userDao.save(user);
        return this.modelMapper.map(updateUser, UserWrapper.class);

    }

    @Override
    public UserWrapper getUserById(Integer userId) {

        User user = this.userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return this.modelMapper.map(user, UserWrapper.class);
    }

    @Override
    public List<UserWrapper> getAllUsers() {
        List<User> allUsers = userDao.findAll();

        List<UserWrapper> userWrappers = new ArrayList<>();
        for (User user : allUsers) {
            UserWrapper userWrapper = this.modelMapper.map(user, UserWrapper.class);
            userWrappers.add(userWrapper);
        }
        return userWrappers;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userDao.delete(user);
    }

}
