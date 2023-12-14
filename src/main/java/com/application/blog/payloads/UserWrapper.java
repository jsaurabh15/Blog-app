package com.application.blog.payloads;

import com.application.blog.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserWrapper {

    private Integer id;

    @NotEmpty
    @Size(min = 4, message = "Username must be at least of 4 characters !!")
    private String name;

    @Email(message = "Email address is not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 6, max = 10, message = "Password must be at least of 6 characters and maximum upto 10 characters !!")
    private String password;

    @NotEmpty
    private String about;
    private Set<RoleWrapper> roles = new HashSet<>();

}
