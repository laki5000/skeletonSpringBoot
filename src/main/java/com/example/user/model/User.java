package com.example.user.model;

import com.example.utils.model.BaseModelForCreation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/** User entity. */
@Entity
@Getter
@Setter
public class User extends BaseModelForCreation {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
