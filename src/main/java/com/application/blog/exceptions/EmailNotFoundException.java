package com.application.blog.exceptions;

import lombok.Data;

@Data
public class EmailNotFoundException extends RuntimeException{

    private String email;


    public EmailNotFoundException(String email) {
        super(String.format("User not found with email id : %s", email));
        this.email = email;
    }
}
