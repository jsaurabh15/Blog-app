package com.application.blog.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Data
@Entity
public class Role {
    @Id
    private Integer id;
    private String name;

}
