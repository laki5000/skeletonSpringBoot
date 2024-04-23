package com.example.user.model;

import com.example.utils.model.BaseModelForCreation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

/** User entity. */
@Entity
@Getter
public class User extends BaseModelForCreation {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
