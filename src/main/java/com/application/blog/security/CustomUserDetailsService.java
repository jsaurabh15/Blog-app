package com.application.blog.security;

import com.application.blog.dao.UserDao;
import com.application.blog.exceptions.EmailNotFoundException;
import com.application.blog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userDao.findByEmail(username).
                orElseThrow(() -> new EmailNotFoundException(username));
        return user;

    }
}
